package com.michaelflisar.kotpreferences.core.interfaces

import com.michaelflisar.kotpreferences.core.SettingsChangeEvent
import com.michaelflisar.kotpreferences.core.classes.StorageDataType
import com.michaelflisar.kotpreferences.core.classes.StorageKey
import kotlinx.coroutines.flow.Flow

interface Storage {

    val changeFlow: Flow<SettingsChangeEvent<*>>
    val cache: Boolean

    suspend fun clear()
    suspend fun clearDeprecatedKeys(keysToKeep: List<StorageKey<*>>)

    fun <T : Any?> get(key: StorageKey<*>, defaultValue: T): Flow<T>
    suspend fun <T : Any?> set(key: StorageKey<*>, value: T)
    suspend fun clear(key: StorageKey<*>)

    suspend fun <T : Any?> onValueChanged(setting: StorageSetting<T>, value: T)
}