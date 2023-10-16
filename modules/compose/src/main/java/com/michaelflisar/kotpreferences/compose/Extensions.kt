package com.michaelflisar.kotpreferences.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Composable
fun <T> StorageSetting<T>.collectAsState(initialValue: T? = getCached()): State<T?> {
    return flow.collectAsState(initial = initialValue)
}

@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycle(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    initialValue: T? = getCached()
): State<T?> {
    return flow.collectAsStateWithLifecycle(
        initialValue = initialValue,
        lifecycle,
        minActiveState,
        context
    )
}

@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    initialValue: T? = getCached()
): State<T?> {
    return flow.collectAsStateWithLifecycle(
        initialValue = initialValue,
        lifecycleOwner,
        minActiveState,
        context
    )
}

@Composable
fun <T> StorageSetting<T>.collectAsStateNotNull(
    initialValue: T = getCached() ?: defaultValue
): State<T> {
    return flow.collectAsState(initial = initialValue)
}

@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycleNotNull(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    initialValue: T = getCached() ?: defaultValue
): State<T> {
    return flow.collectAsStateWithLifecycle(
        initialValue = initialValue,
        lifecycle,
        minActiveState,
        context
    )
}

@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycleNotNull(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    initialValue: T = getCached() ?: defaultValue
): State<T> {
    return flow.collectAsStateWithLifecycle(
        initialValue = initialValue,
        lifecycleOwner,
        minActiveState,
        context
    )
}