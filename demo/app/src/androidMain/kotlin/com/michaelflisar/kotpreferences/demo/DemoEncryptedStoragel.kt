package com.michaelflisar.kotpreferences.demo

import com.michaelflisar.kotpreferences.core.initialisation.SettingSetup
import com.michaelflisar.kotpreferences.encryption.aes.StorageEncryptionAES
import com.michaelflisar.kotpreferences.storage.datastore.DataStoreStorage
import com.michaelflisar.kotpreferences.storage.datastore.create

object DemoEncryptedStorage {

    private const val ALGORITHM = StorageEncryptionAES.DEFAULT_ALGORITHM
    private const val KEY_ALGORITHM = StorageEncryptionAES.DEFAULT_KEY_ALGORITHM
    // if you use some random salt/iv, make sure to persist them somewhere...
    // in this demo we use a manually defined salt/iv
    // for generating random ones check out StorageEncryptionAES::generateKey and StorageEncryptionAES::generateIv...
    private val KEY = StorageEncryptionAES.getKeyFromPassword(KEY_ALGORITHM, "demo", "salt")
    private val BYTE_ARRAY = listOf(0x16, 0x09, 0xc0, 0x4d, 0x4a, 0x09, 0xd2, 0x46, 0x71, 0xcc, 0x32, 0xb7, 0xd2, 0x91, 0x8a, 0x9c)
        .map { it.toByte() }
        .toByteArray()
    private val IV = StorageEncryptionAES.getIv(BYTE_ARRAY) // byte array must be 16 bytes!
    private val ENCRYPTION = StorageEncryptionAES(ALGORITHM, KEY, IV)

    fun create() = DataStoreStorage.create(
        name = "demo_encrypted_settings",
        cache = SettingSetup.ENABLE_CACHING, // false by default, only relevant for blocking reads
        encryption = ENCRYPTION
    )

}