package com.michaelflisar.kotpreferences.core

import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting

actual fun <T: Any?> StorageSetting<T>.getValueNotNull() : T = getCachedValue() ?: value

actual fun <T: Any?> StorageSetting<T>.tryGetValueNotNull() : T? = getCachedValue() ?: value