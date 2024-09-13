package com.michaelflisar.kotpreferences.core

import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting

class SettingsChangeEvent<T : Any?>(
    val setting: StorageSetting<T>,
    val value: T
)