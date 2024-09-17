package com.michaelflisar.kotpreferences.core.interfaces

import com.michaelflisar.kotpreferences.core.SettingsModel
import com.michaelflisar.kotpreferences.core.classes.SettingsDataType
import com.michaelflisar.kotpreferences.core.classes.StorageDataType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.properties.ReadOnlyProperty

interface StorageSetting<T : Any?> : ReadOnlyProperty<SettingsModel, StorageSetting<T>> {

    val key: String
    val defaultValue: T

    val flow: Flow<T>

    val storageDataType: StorageDataType

    /*
     * returns a cached value or retrieves the value in a blocking way
     */
    val value: T

    suspend fun update(value: T)
    suspend fun read(): T = flow.first()

    fun observeOnce(
        scope: CoroutineScope,
        transformer: ((Flow<T>) -> Flow<T>)? = null,
        context: CoroutineContext = Dispatchers.Main,
        callback: ((value: T) -> Unit)
    ) = flow
        .take(1)
        .onEach {
            withContext(context) {
                callback(it)
            }
        }
        .launchIn(scope)

    fun observe(
        scope: CoroutineScope,
        transformer: ((Flow<T>) -> Flow<T>)? = null,
        context: CoroutineContext = Dispatchers.Main,
        callback: ((value: T) -> Unit)
    ) = flow
        .let {
            transformer?.invoke(it) ?: it
        }
        .onEach {
            withContext(context) {
                callback(it)
            }
        }
        .launchIn(scope)

    suspend fun reset(): Boolean {
        return if (value != defaultValue) {
            update(defaultValue)
            true
        } else false
    }

    fun getCached(): T?
}