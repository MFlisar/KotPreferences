package com.michaelflisar.kotpreferences.core


import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlin.coroutines.CoroutineContext

@RequiresOptIn
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
annotation class InternalApi

/*
 * On platforms:
 * - with runBlocking support:
 *   returns cached value ?: value (value is read in a blocking way)
 *   => always returns the current value!
 * - without runBlocking support:
 *   return cached value ?: defaultValue
 *   => may return default value before returning the current value
 */
@InternalApi
expect fun <T: Any?> StorageSetting<T>.getValueNotNull() : T

/*
 * On platforms:
 * - with runBlocking support:
 *   returns cached value ?: value (value is read in a blocking way)
 *   => always returns the current value!
 * - without runBlocking support:
 *   return cached value ?: NULL
 *   => may return NULL before returning the current value
 */
@InternalApi
expect fun <T: Any?> StorageSetting<T>.tryGetValueNotNull() : T?

@InternalApi
expect val StorageContext: CoroutineContext