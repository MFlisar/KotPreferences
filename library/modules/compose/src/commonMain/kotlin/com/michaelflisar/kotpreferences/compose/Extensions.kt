package com.michaelflisar.kotpreferences.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/* --8<-- [start: collectAsState1] */
@Composable
fun <T> StorageSetting<T>.collectAsState(initialValue: T? = getCached()): State<T?>
/* --8<-- [end: collectAsState1] */
{
    return flow.collectAsState(initial = initialValue)
}


/* --8<-- [start: collectAsState2] */
@Composable
fun <T, X> StorageSetting<T>.collectAsState(
    initialValue: T? = getCached(),
    mapper: (T) -> X
): State<X?>
/* --8<-- [end: collectAsState2] */
{
    return flow.map { mapper(it) }.collectAsState(initial = initialValue?.let { mapper(it) })
}


/* --8<-- [start: collectAsStateNotNull1] */
@Composable
fun <T> StorageSetting<T>.collectAsStateNotNull(
    initialValue: T = getCached() ?: value
): State<T>
/* --8<-- [end: collectAsStateNotNull1] */
{
    return flow.collectAsState(initial = initialValue)
}


/* --8<-- [start: collectAsStateNotNull2] */
@Composable
fun <T, X> StorageSetting<T>.collectAsStateNotNull(
    initialValue: T = getCached() ?: value,
    mapper: (T) -> X
): State<X>
/* --8<-- [end: collectAsStateNotNull2] */
{
    return flow.map { mapper(it) }.collectAsState(initial = mapper(initialValue))
}

/* --8<-- [start: asMutableState1] */
@Composable
fun <T> StorageSetting<T>.asMutableState(): MutableState<T>
/* --8<-- [end: asMutableState1] */
{
    val state = remember { mutableStateOf(value) }
    LaunchedEffect(Unit) {
        snapshotFlow {
            state.value
        }.collect {
            withContext(Dispatchers.IO) {
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

/* --8<-- [start: asMutableState2] */
@Composable
fun <T, X> StorageSetting<T>.asMutableState(
    mapper: (T) -> X,
    unmapper: (X) -> T,
): MutableState<X>
/* --8<-- [end: asMutableState2] */
{
    val state = remember { mutableStateOf(mapper(value)) }
    LaunchedEffect(Unit) {
        snapshotFlow {
            state.value
        }.collect {
            withContext(Dispatchers.IO) {
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