package com.michaelflisar.kotpreferences.storage.keyvalue

import com.michaelflisar.kotpreferences.core.InternalApi
import com.michaelflisar.kotpreferences.core.StorageContext
import com.michaelflisar.kotpreferences.core.classes.BaseStorage
import com.michaelflisar.kotpreferences.core.classes.StorageDataType
import com.michaelflisar.kotpreferences.core.classes.StorageKey
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

@Suppress("UNCHECKED_CAST")
class KeyValueStorage(
    val exists: () -> Boolean,
    val read: () -> List<KeyValue>,
    val write: suspend (values: List<KeyValue>) -> Unit,
    private val encryption: StorageEncryption? = null,
    override val cache: Boolean = true,
) : BaseStorage() {

    companion object {

    }

    private val fileExisted: Boolean
    private val flows: HashMap<String, Flow<KeyValueEntry<*>>> = HashMap()
    private val data = KeyValueData()

    init {
        fileExisted = exists()
        data.addLines(read())
    }

    override suspend fun clear() {
        data.clear()
        @OptIn(InternalApi::class)
        withContext(StorageContext) {
            write(data.getAllLines(encryption))
        }
    }

    // -----------------
    // Getter / Setter
    // -----------------

    override fun <T> get(key: StorageKey<*>, defaultValue: T): Flow<T> {
        return when (key.type) {
            is StorageDataType.Nullable -> {
                getX<T>(key, defaultValue, null as T)
            }

            is StorageDataType.NotNullable -> {
                getX<T>(key, defaultValue, defaultValue)
            }
        }.map { it.entry }
    }

    override suspend fun <T> set(key: StorageKey<*>, value: T) {
        when (key.type) {
            StorageDataType.String -> setX(key, value as String)
            StorageDataType.Boolean -> setX(key, value as Boolean)
            StorageDataType.Int -> setX(key, value as Int)
            StorageDataType.Long -> setX(key, value as Long)
            StorageDataType.Float -> setX(key, value as Float)
            StorageDataType.Double -> setX(key, value as Double)
            StorageDataType.StringSet -> setX(key, value as Set<String>)
            StorageDataType.NullableString -> setX(key, value as String?)
            StorageDataType.NullableBoolean -> setX(key, value as Boolean?)
            StorageDataType.NullableInt -> setX(key, value as Int?)
            StorageDataType.NullableLong -> setX(key, value as Long?)
            StorageDataType.NullableFloat -> setX(key, value as Float?)
            StorageDataType.NullableDouble -> setX(key, value as String)
        }
    }

    override suspend fun clear(key: StorageKey<*>) {
        clearX(key)
    }

    override suspend fun clearDeprecatedKeys(keysToKeep: List<StorageKey<*>>) {
        val existingKeys = data.getAllKeys()
        val keysToKeep = keysToKeep.map { it.key }.toSet()
        val keysToRemove = existingKeys - keysToKeep
        if (keysToRemove.isEmpty())
            return
        for (key in keysToRemove) {
            data.removeEntry(key)
        }
        write(data.getAllLines(encryption))
    }

    // --------------
    // Helper functions
    // --------------

    private fun <T> getX(
        key: StorageKey<*>,
        defaultValue: T,
        empty: T,
    ): Flow<KeyValueEntry<T>> {
        val flow = flows[key.key]
        if (flow != null) {
            return flow as Flow<KeyValueEntry<T>>
        }
        val entry = data.getEntry<T>(key.key, key.type, encryption) ?: run {
            if (fileExisted) {
                KeyValueEntry(empty, key.type)
            } else {
                KeyValueEntry(defaultValue, key.type)
            }
        }
        val flow2 = MutableStateFlow(entry)
        flows[key.key] = flow2 as Flow<KeyValueEntry<*>>
        data.addEntry(key.key, entry)
        return flow2
    }

    private suspend fun <T> setX(
        key: StorageKey<*>,
        value: T,
    ) {
        val d = KeyValueEntry(value, key.type)
        flows[key.key]?.let { it as MutableStateFlow<KeyValueEntry<T>> }?.also {
            it.emit(d)
        } ?: MutableStateFlow(d).also {
            flows[key.key] = it as MutableStateFlow<KeyValueEntry<*>>
        }
        data.addEntry(key.key, d)
        write(data.getAllLines(encryption))
    }

    private suspend fun clearX(
        key: StorageKey<*>,
    ) {
        data.removeEntry(key.key)
        write(data.getAllLines(encryption))
    }
}