package com.michaelflisar.kotpreferences.core


import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlin.coroutines.CoroutineContext

@RequiresOptIn
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
annotation class InternalApi

@InternalApi
expect fun <T: Any?> StorageSetting<T>.getValueNotNull() : T
@InternalApi
expect fun <T: Any?> StorageSetting<T>.tryGetValueNotNull() : T?

@InternalApi
expect val StorageContext: CoroutineContext