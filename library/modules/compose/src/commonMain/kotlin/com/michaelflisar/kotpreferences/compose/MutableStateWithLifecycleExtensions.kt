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
@Composable
fun <T> StorageSetting<T>.asMutableStateWithLifecycle(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
): MutableState<T?>  {
    val state = collectAsStateWithLifecycle(lifecycle, minActiveState, context, tryGetValueNotNull())
    return state.asMutableState {
        withContext(StorageContext) {
            @Suppress("UNCHECKED_CAST")
            update(it as T)
        }
    }
}

@OptIn(InternalApi::class)
@Composable
fun <T> StorageSetting<T>.asMutableStateWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
): MutableState<T?>  {
    val state = collectAsStateWithLifecycle(lifecycleOwner, minActiveState, context, tryGetValueNotNull())
    return state.asMutableState {
        withContext(StorageContext) {
            @Suppress("UNCHECKED_CAST")
            update(it as T)
        }
    }
}

@OptIn(InternalApi::class)
@Composable
fun <T, X> StorageSetting<T>.asMutableStateWithLifecycle(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
    unmapper: (X) -> T,
): MutableState<X?>  {
    val state = collectAsStateWithLifecycle(lifecycle, minActiveState, context, tryGetValueNotNull(), mapper)
    return state.asMutableState {
        withContext(StorageContext) {
            @Suppress("UNCHECKED_CAST")
            update(unmapper(it as X))
        }
    }
}

@OptIn(InternalApi::class)
@Composable
fun <T, X> StorageSetting<T>.asMutableStateWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
    unmapper: (X) -> T,
): MutableState<X?>  {
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
@Composable
fun <T> StorageSetting<T>.asMutableStateWithLifecycleNotNull(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
): MutableState<T> {
    val state = collectAsStateWithLifecycleNotNull(lifecycle, minActiveState, context, getValueNotNull())
    return state.asMutableState {
        withContext(StorageContext) {
            update(it)
        }
    }
}

@OptIn(InternalApi::class)
@Composable
fun <T> StorageSetting<T>.asMutableStateWithLifecycleNotNull(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
): MutableState<T> {
    val state = collectAsStateWithLifecycleNotNull(lifecycleOwner, minActiveState, context, getValueNotNull())
    return state.asMutableState {
        withContext(StorageContext) {
            update(it)
        }
    }
}

@OptIn(InternalApi::class)
@Composable
fun <T : Any, X : Any> StorageSetting<T>.asMutableStateWithLifecycleNotNull(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
    unmapper: (X) -> T,
): MutableState<X> {
    val state = collectAsStateWithLifecycleNotNull(lifecycle, minActiveState, context, getValueNotNull(), mapper)
    return state.asMutableState {
        withContext(StorageContext) {
            update(unmapper(it))
        }
    }
}

@OptIn(InternalApi::class)
@Composable
fun <T : Any, X : Any> StorageSetting<T>.asMutableStateWithLifecycleNotNull(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
    unmapper: (X) -> T,
): MutableState<X> {
    val state = collectAsStateWithLifecycleNotNull(lifecycleOwner, minActiveState, context, getValueNotNull(), mapper)
    return state.asMutableState {
        withContext(StorageContext) {
            update(unmapper(it))
        }
    }
}