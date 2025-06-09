package com.michaelflisar.kotpreferences.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import com.michaelflisar.kotpreferences.core.InternalApi
import com.michaelflisar.kotpreferences.core.StorageContext
import com.michaelflisar.kotpreferences.core.getValueNotNull
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import com.michaelflisar.kotpreferences.core.tryGetValueNotNull
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.withContext

@OptIn(InternalApi::class)
/* --8<-- [start: asMutableState1] */
@Composable
inline fun <reified T> StorageSetting<T>.asMutableState(): MutableState<T?>
        /* --8<-- [end: asMutableState1] */ {
    val state = remember { mutableStateOf(tryGetValueNotNull()) }
    LaunchedEffect(Unit) {
        snapshotFlow {
            state.value
        }
            .drop(1)
            .collect {
                withContext(StorageContext) {
                    if (it is T) {
                        update(it)
                    }
                }
            }
    }
    LaunchedEffect(Unit) {
        flow.collect {
            state.value = it
        }
    }
    return state
}

@OptIn(InternalApi::class)
/* --8<-- [start: asMutableState2] */
@Composable
inline fun <reified T, reified X> StorageSetting<T>.asMutableState(
    crossinline mapper: (T) -> X,
    crossinline unmapper: (X) -> T,
): MutableState<X?>
        /* --8<-- [end: asMutableState2] */ {
    val state = remember { mutableStateOf(tryGetValueNotNull()?.let(mapper)) }
    LaunchedEffect(Unit) {
        snapshotFlow {
            state.value
        }.drop(1).collect {
            @OptIn(InternalApi::class)
            withContext(StorageContext) {
                if (it is X) {
                    update(unmapper(it))
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        flow.collect {
            state.value = mapper(it)
        }
    }
    return state
}

@OptIn(InternalApi::class)
/* --8<-- [start: asMutableState3] */
@Composable
fun <T> StorageSetting<T>.asMutableStateNotNull(): MutableState<T>
        /* --8<-- [end: asMutableState3] */ {
    val state = remember { mutableStateOf(getValueNotNull()) }
    LaunchedEffect(Unit) {
        snapshotFlow {
            state.value
        }
            .drop(1)
            .collect {
                withContext(StorageContext) {
                    update(it)
                }
            }
    }
    LaunchedEffect(Unit) {
        flow.collect {
            state.value = it
        }
    }
    return state
}

@OptIn(InternalApi::class)
/* --8<-- [start: asMutableState4] */
@Composable
fun <T : Any, X : Any> StorageSetting<T>.asMutableStateNotNull(
    mapper: (T) -> X,
    unmapper: (X) -> T,
): MutableState<X>
        /* --8<-- [end: asMutableState4] */ {
    val state = remember { mutableStateOf(mapper(getValueNotNull())) }
    LaunchedEffect(Unit) {
        snapshotFlow {
            state.value
        }.drop(1).collect {
            @OptIn(InternalApi::class)
            withContext(StorageContext) {
                update(unmapper(it))
            }
        }
    }
    LaunchedEffect(Unit) {
        flow.collect {
            state.value = mapper(it)
        }
    }
    return state
}