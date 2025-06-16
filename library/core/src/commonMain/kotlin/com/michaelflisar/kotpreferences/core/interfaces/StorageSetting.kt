package com.michaelflisar.kotpreferences.core.interfaces

import com.michaelflisar.kotpreferences.core.SettingsModel
import com.michaelflisar.kotpreferences.core.classes.StorageDataType
import kotlinx.coroutines.flow.*
import kotlin.properties.ReadOnlyProperty

interface StorageSetting<T : Any?> : ReadOnlyProperty<SettingsModel, StorageSetting<T>> {

    val key: String
    val defaultValue: T

    val flow: Flow<T>

    val storageDataType: StorageDataType

    suspend fun update(value: T)
    suspend fun read(): T = flow.first()

    suspend fun reset(): Boolean {
        return if (read() != defaultValue) {
            update(defaultValue)
            true
        } else false
    }

    fun getCachedValue(): T?

    fun getCache(): Cache<T>?

    class Cache<T : Any?>(val data: T)
}