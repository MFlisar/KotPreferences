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
/* begin-snippet: CollectAsStateWithLifecycleExtensions::collectAsStateWithLifecycle1 */
@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycle(
    lifecycle: Lifecycle,
    initialValue: T? = tryGetValueNotNull(),
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
): State<T?>
/* end-snippet */{
    return flow.collectAsStateWithLifecycle(
        initialValue = initialValue,
        lifecycle = lifecycle,
        minActiveState = minActiveState,
        context = context
    )
}

@OptIn(InternalApi::class)
/* begin-snippet: CollectAsStateWithLifecycleExtensions::collectAsStateWithLifecycle2 */
@Composable
fun <T, X> StorageSetting<T>.collectAsStateWithLifecycle(
    lifecycle: Lifecycle,
    initialValue: T? = tryGetValueNotNull(),
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
): State<X?>
/* end-snippet */ {
    return flow.map { mapper(it) }.collectAsStateWithLifecycle(
        initialValue = initialValue?.let { mapper(it) },
        lifecycle = lifecycle,
        minActiveState = minActiveState,
        context = context
    )
}

@OptIn(InternalApi::class)
/* begin-snippet: CollectAsStateWithLifecycleExtensions::collectAsStateWithLifecycle3 */
@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycle(
    initialValue: T? = tryGetValueNotNull(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
): State<T?>
/* end-snippet */ {
    return flow.collectAsStateWithLifecycle(
        initialValue = initialValue,
        lifecycleOwner = lifecycleOwner,
        minActiveState = minActiveState,
        context = context
    )
}

@OptIn(InternalApi::class)
/* begin-snippet: CollectAsStateWithLifecycleExtensions::collectAsStateWithLifecycle4 */
@Composable
fun <T, X> StorageSetting<T>.collectAsStateWithLifecycle(
    initialValue: T? = tryGetValueNotNull(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
): State<X?>
/* end-snippet */ {
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
/* begin-snippet: CollectAsStateWithLifecycleExtensions::collectAsStateWithLifecycleNotNull1 */
@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycleNotNull(
    lifecycle: Lifecycle,
    initialValue: T = getValueNotNull(),
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
): State<T>
/* end-snippet */ {
    return flow.collectAsStateWithLifecycle(
        initialValue = initialValue,
        lifecycle = lifecycle,
        minActiveState = minActiveState,
        context = context
    )
}

@OptIn(InternalApi::class)
/* begin-snippet: CollectAsStateWithLifecycleExtensions::collectAsStateWithLifecycleNotNull2 */
@Composable
fun <T, X> StorageSetting<T>.collectAsStateWithLifecycleNotNull(
    lifecycle: Lifecycle,
    initialValue: T = getValueNotNull(),
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
): State<X>
/* end-snippet */ {
    return flow.map { mapper(it) }.collectAsStateWithLifecycle(
        initialValue = mapper(initialValue),
        lifecycle = lifecycle,
        minActiveState = minActiveState,
        context = context
    )
}

@OptIn(InternalApi::class)
/* begin-snippet: CollectAsStateWithLifecycleExtensions::collectAsStateWithLifecycleNotNull3 */
@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycleNotNull(
    initialValue: T = getValueNotNull(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
): State<T>
/* end-snippet */ {
    return flow.collectAsStateWithLifecycle(
        initialValue = initialValue,
        lifecycleOwner = lifecycleOwner,
        minActiveState = minActiveState,
        context = context
    )
}

@OptIn(InternalApi::class)
/* begin-snippet: CollectAsStateWithLifecycleExtensions::collectAsStateWithLifecycleNotNull4 */
@Composable
fun <T, X> StorageSetting<T>.collectAsStateWithLifecycleNotNull(
    initialValue: T = getValueNotNull(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
): State<X>
/* end-snippet */ {
    return flow.map { mapper(it) }.collectAsStateWithLifecycle(
        initialValue = mapper(initialValue),
        lifecycleOwner = lifecycleOwner,
        minActiveState = minActiveState,
        context = context
    )
}