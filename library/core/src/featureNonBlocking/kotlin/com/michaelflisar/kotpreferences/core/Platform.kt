package com.michaelflisar.kotpreferences.core

import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

actual fun <T: Any?> StorageSetting<T>.getValueNotNull() : T = getCachedValue() ?: defaultValue
actual fun <T: Any?> StorageSetting<T>.tryGetValueNotNull() : T? = getCachedValue()

actual val StorageContext: CoroutineContext = Dispatchers.Default