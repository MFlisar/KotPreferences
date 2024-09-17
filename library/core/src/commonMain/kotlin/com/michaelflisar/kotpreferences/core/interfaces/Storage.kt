package com.michaelflisar.kotpreferences.core.interfaces

import com.michaelflisar.kotpreferences.core.SettingsChangeEvent
import com.michaelflisar.kotpreferences.core.classes.StorageDataType
import kotlinx.coroutines.flow.Flow

interface Storage {

    val changeFlow: Flow<SettingsChangeEvent<*>>
    val cache: Boolean

    suspend fun clear()

    fun <T : Any?> get(type: StorageDataType, key: String, defaultValue: T): Flow<T>
    suspend fun <T : Any?> set(type: StorageDataType, key: String, value: T)

    suspend fun <T : Any?> onValueChanged(setting: StorageSetting<T>, value: T)
}