package com.michaelflisar.kotpreferences.storage.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.michaelflisar.kotpreferences.core.classes.BaseStorage
import com.michaelflisar.kotpreferences.core.classes.StorageDataType
import com.michaelflisar.kotpreferences.core.classes.SetConverter
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class DataStoreStorage(
    private val dataStore: DataStore<Preferences>,
    private val encryption: StorageEncryption? = null,
    override val cache: Boolean = true
) : BaseStorage() {

    companion object {

    }

    override suspend fun clear() {
        dataStore.edit { it.clear() }
    }

    // -----------------
    // Getter / Setter
    // -----------------

    override fun <T> get(type: StorageDataType, key: String, defaultValue: T): Flow<T> {
        return when (type) {
            StorageDataType.String -> get(key, ::stringPreferencesKey, defaultValue as String, type.base)
            StorageDataType.Boolean -> get(key, ::booleanPreferencesKey, defaultValue as Boolean, type.base)
            StorageDataType.Int -> get(key, ::intPreferencesKey, defaultValue as Int, type.base)
            StorageDataType.Long ->get(key, ::longPreferencesKey, defaultValue as Long, type.base)
            StorageDataType.Float -> get(key, ::floatPreferencesKey, defaultValue as Float, type.base)
            StorageDataType.Double -> get(key, ::doublePreferencesKey, defaultValue as Double, type.base)
            StorageDataType.StringSet -> get(key, ::stringSetPreferencesKey, defaultValue as Set<String>, type.base)
            StorageDataType.NullableString -> get(key, ::stringPreferencesKey, defaultValue as String?, type.base)
            StorageDataType.NullableBoolean -> get(key, ::booleanPreferencesKey, defaultValue as Boolean?, type.base)
            StorageDataType.NullableInt -> get(key, ::intPreferencesKey, defaultValue as Int?, type.base)
            StorageDataType.NullableLong -> get(key, ::longPreferencesKey, defaultValue as Long?, type.base)
            StorageDataType.NullableFloat -> get(key, ::floatPreferencesKey, defaultValue as Float?, type.base)
            StorageDataType.NullableDouble -> get(key, ::doublePreferencesKey, defaultValue as Double?, type.base)
        } as Flow<T>
    }

    override suspend fun <T> set(type: StorageDataType, key: String, value: T) {
        return when (type) {
            StorageDataType.String -> set(key, ::stringPreferencesKey, value as String, type.base)
            StorageDataType.Boolean -> set(key, ::booleanPreferencesKey, value as Boolean, type.base)
            StorageDataType.Int -> set(key, ::intPreferencesKey, value as Int, type.base)
            StorageDataType.Long ->set(key, ::longPreferencesKey, value as Long, type.base)
            StorageDataType.Float -> set(key, ::floatPreferencesKey, value as Float, type.base)
            StorageDataType.Double -> set(key, ::doublePreferencesKey, value as Double, type.base)
            StorageDataType.StringSet -> set(key, ::stringSetPreferencesKey, value as Set<String>, type.base)
            StorageDataType.NullableString -> set(key, ::stringPreferencesKey, value as String?, type.base)
            StorageDataType.NullableBoolean -> set(key, ::booleanPreferencesKey, value as Boolean?, type.base)
            StorageDataType.NullableInt -> set(key, ::intPreferencesKey, value as Int?, type.base)
            StorageDataType.NullableLong -> set(key, ::longPreferencesKey, value as Long?, type.base)
            StorageDataType.NullableFloat -> set(key, ::floatPreferencesKey, value as Float?, type.base)
            StorageDataType.NullableDouble -> set(key, ::doublePreferencesKey, value as Double?, type.base)
        }
    }

    // --------------
    // Helper functions
    // --------------

    private fun <T2 : Any, T : T2?> get(
        key: String,
        keyCreator: (String) -> Preferences.Key<T2>,
        defaultValue: T,
        type: StorageDataType.NotNullable
    ): Flow<T> {
        return dataStore.data.map { settings ->
            if (encryption != null) {
                settings[stringPreferencesKey(key)]?.let { encryption.decrypt(it, type) }
            } else {
                settings[keyCreator(key)]
            } ?: defaultValue
        }.map { it as T }.distinctUntilChanged()
    }

    private suspend fun <T2 : Any, T : T2?> set(
        key: String,
        keyCreator: (String) -> Preferences.Key<T2>,
        value: T,
        type: StorageDataType.NotNullable
    ) {
        dataStore.edit { settings ->
            value?.let {
                if (encryption != null) {
                    settings[stringPreferencesKey(key)] = encryption.encrypt(it, type)
                } else {
                    settings[keyCreator(key)] = it
                }
            }
                ?: settings.remove(booleanPreferencesKey(key))
        }
    }
}