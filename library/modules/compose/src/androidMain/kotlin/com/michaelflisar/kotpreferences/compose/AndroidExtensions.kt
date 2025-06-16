package com.michaelflisar.kotpreferences.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.michaelflisar.kotpreferences.core.getValueNotNull
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import com.michaelflisar.kotpreferences.core.tryGetValueNotNull
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycle(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    initialValue: T? = tryGetValueNotNull()
): State<T?> {
    return flow.collectAsStateWithLifecycle(
        initialValue = initialValue,
        lifecycle = lifecycle,
        minActiveState = minActiveState,
        context = context
    )
}

@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    initialValue: T? = tryGetValueNotNull()
): State<T?> {
    return flow.collectAsStateWithLifecycle(
        initialValue = initialValue,
        lifecycleOwner = lifecycleOwner,
        minActiveState = minActiveState,
        context = context
    )
}

@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycleNotNull(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    initialValue: T = getValueNotNull()
): State<T> {
    return flow.collectAsStateWithLifecycle(
        initialValue = initialValue,
        lifecycle = lifecycle,
        minActiveState = minActiveState,
        context = context
    )
}

@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycleNotNull(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    initialValue: T = getValueNotNull()
): State<T> {
    return flow.collectAsStateWithLifecycle(
        initialValue = initialValue,
        lifecycleOwner = lifecycleOwner,
        minActiveState = minActiveState,
        context = context
    )
}