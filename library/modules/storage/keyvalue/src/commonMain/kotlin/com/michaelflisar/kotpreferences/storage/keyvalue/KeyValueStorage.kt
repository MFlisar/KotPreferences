package com.michaelflisar.kotpreferences.storage.keyvalue

import com.michaelflisar.kotpreferences.core.classes.BaseStorage
import com.michaelflisar.kotpreferences.core.classes.StorageDataType
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okio.Path

@Suppress("UNCHECKED_CAST")
class KeyValueStorage(
    private val filePath: Path,
    private val delimiter: String = "=",
    private val encryption: StorageEncryption? = null,
    override val cache: Boolean = true
) : BaseStorage() {

    companion object {

    }

    private val fileExisted: Boolean
    private val flows: HashMap<String, Flow<KeyValueEntry<*>>> = HashMap()
    private val data = KeyValueData()

    init {
        fileExisted = FileUtil.exists(filePath)
        data.addLines(FileUtil.read(filePath, delimiter))
    }

    override suspend fun clear() {
        data.clear()
        withContext(Dispatchers.IO) {
            FileUtil.save(data.getAllLines(encryption), filePath, delimiter)
        }
    }

    // -----------------
    // Getter / Setter
    // -----------------

    override fun <T> get(type: StorageDataType, key: String, defaultValue: T): Flow<T> {
        return when (type) {
            is StorageDataType.Nullable -> {
                getX<T>(type, key, defaultValue, null as T)
            }

            is StorageDataType.NotNullable -> {
                getX<T>(type, key, defaultValue, defaultValue)
            }
        }.map { it.entry }
    }

    override suspend fun <T> set(type: StorageDataType, key: String, value: T) {
        when (type) {
            StorageDataType.String -> setX(type, key, value as String)
            StorageDataType.Boolean -> setX(type, key, value as Boolean)
            StorageDataType.Int -> setX(type, key, value as Int)
            StorageDataType.Long -> setX(type, key, value as Long)
            StorageDataType.Float -> setX(type, key, value as Float)
            StorageDataType.Double -> setX(type, key, value as Double)
            StorageDataType.StringSet -> setX(type, key, value as Set<String>)
            StorageDataType.NullableString -> setX(type, key, value as String?)
            StorageDataType.NullableBoolean -> setX(type, key, value as Boolean?)
            StorageDataType.NullableInt -> setX(type, key, value as Int?)
            StorageDataType.NullableLong -> setX(type, key, value as Long?)
            StorageDataType.NullableFloat -> setX(type, key, value as Float?)
            StorageDataType.NullableDouble -> setX(type, key, value as String)
        }
    }

    // --------------
    // Helper functions
    // --------------

    private fun <T> getX(
        type: StorageDataType,
        key: String,
        defaultValue: T,
        empty: T
    ): Flow<KeyValueEntry<T>> {
        val flow = flows[key]
        if (flow != null) {
            return flow as Flow<KeyValueEntry<T>>
        }
        val entry = data.getEntry<T>(key, type, encryption) ?: run {
            if (fileExisted) {
                KeyValueEntry(empty, type)
            } else {
                KeyValueEntry(defaultValue, type)
            }
        }
        val flow2 = MutableStateFlow(entry)
        flows[key] = flow2 as Flow<KeyValueEntry<*>>
        data.addEntry(key, entry)
        return flow2
    }

    private suspend fun <T> setX(
        type: StorageDataType,
        key: String,
        value: T
    ) {
        val d = KeyValueEntry(value, type)
        flows[key]?.let { it as MutableStateFlow<KeyValueEntry<T>> }?.also {
            it.emit(d)
        } ?: MutableStateFlow(d).also {
            flows[key] = it as MutableStateFlow<KeyValueEntry<*>>
        }
        data.addEntry(key, d)
        FileUtil.saveAsync(data.getAllLines(encryption), filePath, delimiter)
    }
}