package com.michaelflisar.kotpreferences.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.michaelflisar.kotpreferences.core.InternalApi
import com.michaelflisar.kotpreferences.core.StorageContext
import com.michaelflisar.kotpreferences.core.getValueNotNull
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import com.michaelflisar.kotpreferences.core.tryGetValueNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

// ------------------------
// nullable versions
// ------------------------

@OptIn(InternalApi::class)
/* --8<-- [start: asMutableStateWithLifecycle1] */
@Composable
fun <T> StorageSetting<T>.asMutableStateWithLifecycle(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
): MutableState<T?>
/* --8<-- [end: asMutableStateWithLifecycle1] */ {
    val state = collectAsStateWithLifecycle(lifecycle, minActiveState, context, tryGetValueNotNull())
    return state.asMutableState {
        withContext(StorageContext) {
            @Suppress("UNCHECKED_CAST")
            update(it as T)
        }
    }
}

@OptIn(InternalApi::class)
/* --8<-- [start: asMutableStateWithLifecycle2] */
@Composable
fun <T> StorageSetting<T>.asMutableStateWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
): MutableState<T?>
/* --8<-- [end: asMutableStateWithLifecycle2] */ {
    val state = collectAsStateWithLifecycle(lifecycleOwner, minActiveState, context, tryGetValueNotNull())
    return state.asMutableState {
        withContext(StorageContext) {
            @Suppress("UNCHECKED_CAST")
            update(it as T)
        }
    }
}

@OptIn(InternalApi::class)
/* --8<-- [start: asMutableStateWithLifecycle3] */
@Composable
fun <T, X> StorageSetting<T>.asMutableStateWithLifecycle(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
    unmapper: (X) -> T,
): MutableState<X?>
/* --8<-- [end: asMutableStateWithLifecycle3] */ {
    val state = collectAsStateWithLifecycle(lifecycle, minActiveState, context, tryGetValueNotNull(), mapper)
    return state.asMutableState {
        withContext(StorageContext) {
            @Suppress("UNCHECKED_CAST")
            update(unmapper(it as X))
        }
    }
}

@OptIn(InternalApi::class)
/* --8<-- [start: asMutableStateWithLifecycle4] */
@Composable
fun <T, X> StorageSetting<T>.asMutableStateWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
    unmapper: (X) -> T,
): MutableState<X?>
/* --8<-- [end: asMutableStateWithLifecycle4] */ {
    val state = collectAsStateWithLifecycle(lifecycleOwner, minActiveState, context, tryGetValueNotNull(), mapper)
    return state.asMutableState {
        withContext(StorageContext) {
            @Suppress("UNCHECKED_CAST")
            update(unmapper(it as X))
        }
    }
}

// ------------------------
// not nullable versions
// ------------------------

@OptIn(InternalApi::class)
/* --8<-- [start: asMutableStateWithLifecycleNotNull1] */
@Composable
fun <T> StorageSetting<T>.asMutableStateWithLifecycleNotNull(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
): MutableState<T>
/* --8<-- [end: asMutableStateWithLifecycleNotNull1] */ {
    val state = collectAsStateWithLifecycleNotNull(lifecycle, minActiveState, context, getValueNotNull())
    return state.asMutableState {
        withContext(StorageContext) {
            update(it)
        }
    }
}

@OptIn(InternalApi::class)
/* --8<-- [start: asMutableStateWithLifecycleNotNull2] */
@Composable
fun <T> StorageSetting<T>.asMutableStateWithLifecycleNotNull(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
): MutableState<T>
/* --8<-- [end: asMutableStateWithLifecycleNotNull2] */ {
    val state = collectAsStateWithLifecycleNotNull(lifecycleOwner, minActiveState, context, getValueNotNull())
    return state.asMutableState {
        withContext(StorageContext) {
            update(it)
        }
    }
}

@OptIn(InternalApi::class)
/* --8<-- [start: asMutableStateWithLifecycleNotNull3] */
@Composable
fun <T : Any, X : Any> StorageSetting<T>.asMutableStateWithLifecycleNotNull(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
    unmapper: (X) -> T,
): MutableState<X>
/* --8<-- [end: asMutableStateWithLifecycleNotNull3] */ {
    val state = collectAsStateWithLifecycleNotNull(lifecycle, minActiveState, context, getValueNotNull(), mapper)
    return state.asMutableState {
        withContext(StorageContext) {
            update(unmapper(it))
        }
    }
}

@OptIn(InternalApi::class)
/* --8<-- [start: asMutableStateWithLifecycleNotNull4] */
@Composable
fun <T : Any, X : Any> StorageSetting<T>.asMutableStateWithLifecycleNotNull(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
    unmapper: (X) -> T,
): MutableState<X>
/* --8<-- [end: asMutableStateWithLifecycleNotNull4] */ {
    val state = collectAsStateWithLifecycleNotNull(lifecycleOwner, minActiveState, context, getValueNotNull(), mapper)
    return state.asMutableState {
        withContext(StorageContext) {
            update(unmapper(it))
        }
    }
}