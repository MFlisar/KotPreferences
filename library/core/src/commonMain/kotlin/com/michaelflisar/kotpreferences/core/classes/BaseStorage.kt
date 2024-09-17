package com.michaelflisar.kotpreferences.core.classes

import com.michaelflisar.kotpreferences.core.SettingsChangeEvent
import com.michaelflisar.kotpreferences.core.interfaces.Storage
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseStorage : Storage {

    private val mutableChangeFlow: MutableSharedFlow<SettingsChangeEvent<*>> = MutableSharedFlow()
    override val changeFlow: Flow<SettingsChangeEvent<*>> = mutableChangeFlow

    override suspend fun <T : Any?> onValueChanged(setting: StorageSetting<T>, value: T) {
        mutableChangeFlow.emit(SettingsChangeEvent(setting, value))
    }
}