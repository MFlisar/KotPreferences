package com.michaelflisar.kotpreferences.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.michaelflisar.kotpreferences.core.InternalApi
import com.michaelflisar.kotpreferences.core.StorageContext
import com.michaelflisar.kotpreferences.core.getValueNotNull
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import com.michaelflisar.kotpreferences.core.tryGetValueNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(InternalApi::class)
/* --8<-- [start: asMutableState1] */
@Composable
fun <T> StorageSetting<T>.asMutableState(): MutableState<T?>
        /* --8<-- [end: asMutableState1] */ {
    val state = collectAsState(tryGetValueNotNull())
    return state.asMutableState {
        withContext(StorageContext) {
            @Suppress("UNCHECKED_CAST")
            update(it as T)
        }
    }
}

@OptIn(InternalApi::class)
/* --8<-- [start: asMutableState2] */
@Composable
fun <T, X> StorageSetting<T>.asMutableState(
    mapper: (T) -> X,
    unmapper: (X) -> T,
): MutableState<X?>
        /* --8<-- [end: asMutableState2] */ {

    val state = collectAsState(tryGetValueNotNull(), mapper)
    return state.asMutableState {
        withContext(StorageContext) {
            @Suppress("UNCHECKED_CAST")
            update(unmapper(it as X))
        }
    }
}

@OptIn(InternalApi::class)
/* --8<-- [start: asMutableStateNotNull1] */
@Composable
fun <T> StorageSetting<T>.asMutableStateNotNull(): MutableState<T>
        /* --8<-- [end: asMutableStateNotNull1] */ {
    val state = collectAsStateNotNull(getValueNotNull())
    return state.asMutableState {
        withContext(StorageContext) {
            update(it)
        }
    }
}

@OptIn(InternalApi::class)
/* --8<-- [start: asMutableStateNotNull2] */
@Composable
fun <T : Any, X : Any> StorageSetting<T>.asMutableStateNotNull(
    mapper: (T) -> X,
    unmapper: (X) -> T,
): MutableState<X>
        /* --8<-- [end: asMutableStateNotNull2] */ {
    val state = collectAsStateNotNull(getValueNotNull(), mapper)
    return state.asMutableState {
        withContext(StorageContext) {
            update(unmapper(it))
        }
    }
}

@Composable
private fun <T> State<T>.asMutableState(update: suspend (T) -> Unit): MutableState<T> {
    val coroutineScope = rememberCoroutineScope()
    // local state to be able to change the value IMMEDIATELY
    val localState = remember { mutableStateOf(value) }
    // synchronise local state in case the state changes from outside
    LaunchedEffect(value) {
        if (localState.value != value) {
            localState.value = value
        }
    }
    return object : MutableState<T> {
        override var value: T
            get() = localState.value
            set(newValue) {
                localState.value = newValue
                coroutineScope.launch {
                    update(newValue)
                }
            }

        override fun component1() = value
        override fun component2() = { v: T -> value = v }
    }
}