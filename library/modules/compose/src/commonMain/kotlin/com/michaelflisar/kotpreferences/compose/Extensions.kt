package com.michaelflisar.kotpreferences.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

@Composable
fun <T> StorageSetting<T>.collectAsState(initialValue: T? = getCached()): State<T?> {
    return flow.collectAsState(initial = initialValue)
}

@Composable
fun <T> StorageSetting<T>.collectAsStateNotNull(
    initialValue: T = getCached() ?: value
): State<T> {
    return flow.collectAsState(initial = initialValue)
}

@Composable
fun <T> StorageSetting<T>.asMutableState(): MutableState<T> {
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