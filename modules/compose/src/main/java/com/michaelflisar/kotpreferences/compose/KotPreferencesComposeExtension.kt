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
fun <T> StorageSetting<T>.collectAsState(useDefaultValue: Boolean = false): State<T> {
    return flow.collectAsState(initial = if (useDefaultValue) defaultValue else value)
}

@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycle(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    useDefaultValue: Boolean = false
): State<T> {
    return flow.collectAsStateWithLifecycle(
        initialValue = if (useDefaultValue) defaultValue else value,
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
    useDefaultValue: Boolean = false
): State<T> {
    return flow.collectAsStateWithLifecycle(
        initialValue = if (useDefaultValue) defaultValue else value,
        lifecycleOwner,
        minActiveState,
        context
    )
}