package com.michaelflisar.kotpreferences.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.michaelflisar.kotpreferences.core.InternalApi
import com.michaelflisar.kotpreferences.core.getValueNotNull
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import com.michaelflisar.kotpreferences.core.tryGetValueNotNull
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

// ------------------------
// nullable versions
// ------------------------

@OptIn(InternalApi::class)
/* --8<-- [start: collectAsStateWithLifecycle1] */
@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycle(
    lifecycle: Lifecycle,
    initialValue: T? = tryGetValueNotNull(),
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
): State<T?>
/* --8<-- [end: collectAsStateWithLifecycle1] */ {
    return flow.collectAsStateWithLifecycle(
        initialValue = initialValue,
        lifecycle = lifecycle,
        minActiveState = minActiveState,
        context = context
    )
}

@OptIn(InternalApi::class)
/* --8<-- [start: collectAsStateWithLifecycle2] */
@Composable
fun <T, X> StorageSetting<T>.collectAsStateWithLifecycle(
    lifecycle: Lifecycle,
    initialValue: T? = tryGetValueNotNull(),
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
): State<X?>
/* --8<-- [end: collectAsStateWithLifecycle2] */ {
    return flow.map { mapper(it) }.collectAsStateWithLifecycle(
        initialValue = initialValue?.let { mapper(it) },
        lifecycle = lifecycle,
        minActiveState = minActiveState,
        context = context
    )
}

@OptIn(InternalApi::class)
/* --8<-- [start: collectAsStateWithLifecycle3] */
@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycle(
    initialValue: T? = tryGetValueNotNull(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
): State<T?>
/* --8<-- [end: collectAsStateWithLifecycle3] */ {
    return flow.collectAsStateWithLifecycle(
        initialValue = initialValue,
        lifecycleOwner = lifecycleOwner,
        minActiveState = minActiveState,
        context = context
    )
}

@OptIn(InternalApi::class)
/* --8<-- [start: collectAsStateWithLifecycle4] */
@Composable
fun <T, X> StorageSetting<T>.collectAsStateWithLifecycle(
    initialValue: T? = tryGetValueNotNull(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
): State<X?>
/* --8<-- [end: collectAsStateWithLifecycle4] */ {
    return flow.map { mapper(it) }.collectAsStateWithLifecycle(
        initialValue = initialValue?.let { mapper(it) },
        lifecycleOwner = lifecycleOwner,
        minActiveState = minActiveState,
        context = context
    )
}

// ------------------------
// not nullable versions
// ------------------------

@OptIn(InternalApi::class)
/* --8<-- [start: collectAsStateWithLifecycleNotNull1] */
@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycleNotNull(
    lifecycle: Lifecycle,
    initialValue: T = getValueNotNull(),
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
): State<T>
/* --8<-- [end: collectAsStateWithLifecycleNotNull1] */ {
    return flow.collectAsStateWithLifecycle(
        initialValue = initialValue,
        lifecycle = lifecycle,
        minActiveState = minActiveState,
        context = context
    )
}

@OptIn(InternalApi::class)
/* --8<-- [start: collectAsStateWithLifecycleNotNull2] */
@Composable
fun <T, X> StorageSetting<T>.collectAsStateWithLifecycleNotNull(
    lifecycle: Lifecycle,
    initialValue: T = getValueNotNull(),
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
): State<X>
/* --8<-- [end: collectAsStateWithLifecycleNotNull2] */ {
    return flow.map { mapper(it) }.collectAsStateWithLifecycle(
        initialValue = mapper(initialValue),
        lifecycle = lifecycle,
        minActiveState = minActiveState,
        context = context
    )
}

@OptIn(InternalApi::class)
/* --8<-- [start: collectAsStateWithLifecycleNotNull3] */
@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycleNotNull(
    initialValue: T = getValueNotNull(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
): State<T>
/* --8<-- [end: collectAsStateWithLifecycleNotNull3] */ {
    return flow.collectAsStateWithLifecycle(
        initialValue = initialValue,
        lifecycleOwner = lifecycleOwner,
        minActiveState = minActiveState,
        context = context
    )
}

@OptIn(InternalApi::class)
/* --8<-- [start: collectAsStateWithLifecycleNotNull4] */
@Composable
fun <T, X> StorageSetting<T>.collectAsStateWithLifecycleNotNull(
    initialValue: T = getValueNotNull(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
): State<X>
/* --8<-- [end: collectAsStateWithLifecycleNotNull4] */ {
    return flow.map { mapper(it) }.collectAsStateWithLifecycle(
        initialValue = mapper(initialValue),
        lifecycleOwner = lifecycleOwner,
        minActiveState = minActiveState,
        context = context
    )
}