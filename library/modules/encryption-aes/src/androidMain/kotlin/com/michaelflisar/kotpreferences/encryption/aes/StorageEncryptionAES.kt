package com.michaelflisar.kotpreferences.encryption.aes

import android.util.Base64
import com.michaelflisar.kotpreferences.core.classes.StorageDataType
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption
import java.io.*
import java.security.SecureRandom
import javax.crypto.*
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.LinkedHashSet

class StorageEncryptionAES(
    private val algorithm: String,
    private val key: SecretKey,
    private val iv: IvParameterSpec
) : StorageEncryption {

    companion object {

        private const val ALGORITHM = "AES"
        const val DEFAULT_ALGORITHM = "AES/CBC/PKCS5Padding"
        const val DEFAULT_KEY_ALGORITHM = "PBKDF2WithHmacSHA256"

        fun generateIv(): IvParameterSpec {
            val iv = ByteArray(16)
            SecureRandom().nextBytes(iv)
            return IvParameterSpec(iv)
        }

        fun getIv(iv: ByteArray): IvParameterSpec {
            return IvParameterSpec(iv)
        }

        fun generateKey(keyLength: Int): SecretKey {
            val keyGenerator = KeyGenerator.getInstance(ALGORITHM)
            keyGenerator.init(keyLength)
            return keyGenerator.generateKey()
        }

        fun getKeyFromPassword(
            algorithm: String = DEFAULT_KEY_ALGORITHM,
            password: String,
            salt: String,
            iterations: Int = 65536,
            keyLength: Int = 256
        ): SecretKey {
            val factory: SecretKeyFactory = SecretKeyFactory.getInstance(algorithm)
            val spec = PBEKeySpec(password.toCharArray(), salt.toByteArray(), iterations, keyLength)
            return SecretKeySpec(factory.generateSecret(spec).encoded, ALGORITHM)
        }
    }

    // -------------------------
    // Algorithm
    // -------------------------

    private val encryptCipher by lazy {
        val cipher: Cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)
        cipher
    }

    private val decryptCipher by lazy {
        val cipher: Cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.DECRYPT_MODE, key, iv)
        cipher
    }

    private fun encrypt(data: String): String {
        val cipherText = encryptCipher.doFinal(data.toByteArray())
        return Base64.encodeToString(cipherText, Base64.DEFAULT)
    }

    private fun decrypt(data: String): String {
        val plainText = decryptCipher.doFinal(
            Base64.decode(data, Base64.DEFAULT)
        )
        return String(plainText)
    }

    private fun <T : Serializable> encryptSealedObject(
        data: T
    ): String {
        val sealed = SealedObject(data, encryptCipher)
        val bos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(bos)
        oos.writeObject(sealed)
        oos.close()
        bos.close()
        val bytes = bos.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    private fun <T : Serializable> decryptSealedObject(
        data: String
    ): T {
        val bytes = Base64.decode(data, Base64.DEFAULT)
        val bis = ByteArrayInputStream(bytes)
        val ois = ObjectInputStream(bis)
        val sealed = ois.readObject() as SealedObject
        ois.close()
        bis.close()
        return sealed.getObject(decryptCipher) as T
    }

    // -------------------------
    // Interface implementations
    // -------------------------

    override fun <T> encrypt(value: T, type: StorageDataType.NotNullable) = when (type) {
        StorageDataType.Boolean -> encrypt(value.toString())
        StorageDataType.Double -> encrypt(value.toString())
        StorageDataType.Float -> encrypt(value.toString())
        StorageDataType.Int -> encrypt(value.toString())
        StorageDataType.Long -> encrypt(value.toString())
        StorageDataType.String -> encrypt(value as String)
        StorageDataType.StringSet -> encryptSealedObject(LinkedHashSet(value as Set<String>))
    }

    override fun <T> decrypt(data: String, type: StorageDataType.NotNullable) = when (type) {
        StorageDataType.Boolean -> decrypt(data).toBoolean()
        StorageDataType.Double -> decrypt(data).toDouble()
        StorageDataType.Float -> decrypt(data).toFloat()
        StorageDataType.Int ->  decrypt(data).toInt()
        StorageDataType.Long -> decrypt(data).toLong()
        StorageDataType.String -> decrypt(data)
        StorageDataType.StringSet -> decryptSealedObject<LinkedHashSet<String>>(data)
    } as T
}