package com.michaelflisar.kotpreferences.storage.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
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
import com.michaelflisar.kotpreferences.core.classes.StorageKey
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Suppress("UNCHECKED_CAST")
class DataStoreStorage(
    private val dataStore: DataStore<Preferences>,
    private val encryption: StorageEncryption? = null,
    override val cache: Boolean = true,
) : BaseStorage() {

    companion object {
    }

    override suspend fun clear() {
        dataStore.edit { it.clear() }
    }

    override suspend fun clearDeprecatedKeys(keysToKeep: List<StorageKey<*>>) {
        val keys = keysToKeep.map { it.getPreferenceKeyGeneric() }
        dataStore.edit { settings ->
            settings.asMap().keys.forEach { key ->
                val isKeyToKeep = keys.contains(key)
                if (!isKeyToKeep) {
                    settings.remove(key)
                }
            }
        }
    }

    // -----------------
    // Getter / Setter
    // -----------------

    override fun <T> get(key: StorageKey<*>, defaultValue: T): Flow<T> {
        return when (key.type) {
            StorageDataType.String -> getX(
                key,
                defaultValue as String
            )

            StorageDataType.Boolean -> getX(
                key,
                defaultValue as Boolean
            )

            StorageDataType.Int -> getX(
                key,
                defaultValue as Int
            )

            StorageDataType.Long -> getX(
                key,
                defaultValue as Long
            )

            StorageDataType.Float -> getX(
                key,
                defaultValue as Float
            )

            StorageDataType.Double -> getX(
                key,
                defaultValue as Double
            )

            StorageDataType.StringSet -> getX(
                key,
                defaultValue as Set<String>
            )

            StorageDataType.NullableString -> getX(
                key,
                defaultValue as String?
            )

            StorageDataType.NullableBoolean -> getX(
                key,
                defaultValue as Boolean?
            )

            StorageDataType.NullableInt -> getX(
                key,
                defaultValue as Int?
            )

            StorageDataType.NullableLong -> getX(
                key,
                defaultValue as Long?
            )

            StorageDataType.NullableFloat -> getX(
                key,
                defaultValue as Float?
            )

            StorageDataType.NullableDouble -> getX(
                key,
                defaultValue as Double?
            )
        } as Flow<T>
    }

    override suspend fun <T> set(key: StorageKey<*>, value: T) {
        return when (key.type) {
            StorageDataType.String -> setX(
                key,
                value as String
            )

            StorageDataType.Boolean -> setX(
                key,
                value as Boolean
            )

            StorageDataType.Int -> setX(
                key,
                value as Int
            )

            StorageDataType.Long -> setX(
                key,
                value as Long
            )

            StorageDataType.Float -> setX(
                key,
                value as Float
            )

            StorageDataType.Double -> setX(
                key,
                value as Double
            )

            StorageDataType.StringSet -> setX(
                key,
                value as Set<String>
            )

            StorageDataType.NullableString -> setX(
                key,
                value as String?
            )

            StorageDataType.NullableBoolean -> setX(
                key,
                value as Boolean?
            )

            StorageDataType.NullableInt -> setX(
                key,
                value as Int?
            )

            StorageDataType.NullableLong -> setX(
                key,
                value as Long?
            )

            StorageDataType.NullableFloat -> setX(
                key,
                value as Float?
            )

            StorageDataType.NullableDouble -> setX(
                key,
                value as Double?
            )
        }
    }

    override suspend fun clear(key: StorageKey<*>) {
        dataStore.edit { settings ->
            when (key.type) {
                StorageDataType.String -> settings.clear<String>(key)
                StorageDataType.Boolean -> settings.clear<Boolean>(key)
                StorageDataType.Int -> settings.clear<Int>(key)
                StorageDataType.Long -> settings.clear<Long>(key)
                StorageDataType.Float -> settings.clear<Float>(key)
                StorageDataType.Double -> settings.clear<Double>(key)
                StorageDataType.StringSet -> settings.clear<Set<String>>(key)
                StorageDataType.NullableString -> settings.clear<String>(key)
                StorageDataType.NullableBoolean -> settings.clear<Boolean>(key)
                StorageDataType.NullableInt -> settings.clear<Int>(key)
                StorageDataType.NullableLong -> settings.clear<Long>(key)
                StorageDataType.NullableFloat -> settings.clear<Float>(key)
                StorageDataType.NullableDouble -> settings.clear<Double>(key)
            }
        }
    }

    // --------------
    // Helper functions
    // --------------

    private fun <T > getX(
        key: StorageKey<*>,
        defaultValue: T,
    ): Flow<T> {
        return dataStore.data.map { settings ->
            if (encryption != null) {
                settings[stringPreferencesKey(key.key)]?.let {
                    encryption.decrypt(
                        it,
                        key.type.base
                    )
                }
            } else {
                settings[key.getPreferenceKey<T & Any>()]
            } ?: defaultValue
        }.map { it }.distinctUntilChanged()
    }

    private suspend fun <T> setX(
        key: StorageKey<*>,
        value: T,
    ) {
        dataStore.edit { settings ->
            value?.let {
                if (encryption != null) {
                    settings[stringPreferencesKey(key.key)] = encryption.encrypt(it, key.type.base)
                } else {
                    settings[key.getPreferenceKey()] = it
                }
            } ?: settings.clear<T & Any>(key)
        }
    }

    private fun <T : Any> MutablePreferences.clear(
        key: StorageKey<*>
    ) {
        remove(key.getPreferenceKey<T>())
    }

    private fun <T : Any> StorageKey<*>.getPreferenceKey(): Preferences.Key<T> {
        return when (type) {
            StorageDataType.String -> stringPreferencesKey(key)
            StorageDataType.Boolean -> booleanPreferencesKey(key)
            StorageDataType.Int -> intPreferencesKey(key)
            StorageDataType.Long -> longPreferencesKey(key)
            StorageDataType.Float -> floatPreferencesKey(key)
            StorageDataType.Double -> doublePreferencesKey(key)
            StorageDataType.StringSet -> stringSetPreferencesKey(key)
            StorageDataType.NullableString -> stringPreferencesKey(key)
            StorageDataType.NullableBoolean -> booleanPreferencesKey(key)
            StorageDataType.NullableInt -> intPreferencesKey(key)
            StorageDataType.NullableLong -> longPreferencesKey(key)
            StorageDataType.NullableFloat -> floatPreferencesKey(key)
            StorageDataType.NullableDouble -> doublePreferencesKey(key)
        } as Preferences.Key<T>
    }

    private fun StorageKey<*>.getPreferenceKeyGeneric(): Preferences.Key<*> {
        return when (type) {
            StorageDataType.String -> stringPreferencesKey(key)
            StorageDataType.Boolean -> booleanPreferencesKey(key)
            StorageDataType.Int -> intPreferencesKey(key)
            StorageDataType.Long -> longPreferencesKey(key)
            StorageDataType.Float -> floatPreferencesKey(key)
            StorageDataType.Double -> doublePreferencesKey(key)
            StorageDataType.StringSet -> stringSetPreferencesKey(key)
            StorageDataType.NullableString -> stringPreferencesKey(key)
            StorageDataType.NullableBoolean -> booleanPreferencesKey(key)
            StorageDataType.NullableInt -> intPreferencesKey(key)
            StorageDataType.NullableLong -> longPreferencesKey(key)
            StorageDataType.NullableFloat -> floatPreferencesKey(key)
            StorageDataType.NullableDouble -> doublePreferencesKey(key)
        }
    }
}