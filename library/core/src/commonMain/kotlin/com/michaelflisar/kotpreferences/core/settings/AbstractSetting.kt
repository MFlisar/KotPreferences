package com.michaelflisar.kotpreferences.core.settings

import com.michaelflisar.kotpreferences.core.classes.SettingsDataType
import com.michaelflisar.kotpreferences.core.classes.StorageDataType
import com.michaelflisar.kotpreferences.core.interfaces.Storage
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

abstract class AbstractSetting<T : Any?> : StorageSetting<T> {

    abstract val customKey: String?
    protected abstract val storage: Storage
    protected abstract val cache: Boolean

    protected abstract val settingsDataType: SettingsDataType
    final override val storageDataType: StorageDataType
        get() = settingsDataType.storageDataType

    protected abstract fun createFlow(): Flow<T>
    protected abstract suspend fun persistValue(value: T)

    final override val flow: Flow<T> by lazy {
        createFlow().onEach(::updateCached)
    }

    final override suspend fun update(value: T) {
        persistValue(value)
        onValueChanged(value)
        // make cache update thread save by doing the update on the main thread
        if (storage.cache && cache) {
            withContext(Dispatchers.Main) {
                updateCached(value)
            }
        }
    }

    private suspend fun onValueChanged(value: T) {
        storage.onValueChanged(this, value)
    }

    // ---------
    // caching
    // ---------

    private var cachedData: StorageSetting.Cache<T>? = null

    private fun updateCached(value: T) {
        if (storage.cache && cache) {
            cachedData = StorageSetting.Cache(value)
        }
    }

    override fun getCachedValue() = cachedData?.data

    override fun getCache() = cachedData

}