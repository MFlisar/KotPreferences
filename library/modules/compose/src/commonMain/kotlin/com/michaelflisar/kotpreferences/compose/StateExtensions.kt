package com.michaelflisar.kotpreferences.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.michaelflisar.kotpreferences.core.InternalApi
import com.michaelflisar.kotpreferences.core.getValueNotNull
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.flow.map

/* --8<-- [start: collectAsState1] */
@Composable
fun <T> StorageSetting<T>.collectAsState(
    initialValue: T? = getCachedValue(),
): State<T?>
        /* --8<-- [end: collectAsState1] */ {
    return flow.collectAsState(initial = initialValue)
}

/* --8<-- [start: collectAsState2] */
@Composable
fun <T, X> StorageSetting<T>.collectAsState(
    initialValue: T? = getCachedValue(),
    mapper: (T) -> X,
): State<X?>
        /* --8<-- [end: collectAsState2] */ {
    return flow.map { mapper(it) }.collectAsState(initial = initialValue?.let { mapper(it) })
}

@OptIn(InternalApi::class)
/* --8<-- [start: collectAsStateNotNull1] */
@Composable
fun <T> StorageSetting<T>.collectAsStateNotNull(
    initialValue: T = getValueNotNull(),
): State<T>
        /* --8<-- [end: collectAsStateNotNull1] */ {
    return flow.collectAsState(initial = initialValue)
}

@OptIn(InternalApi::class)
/* --8<-- [start: collectAsStateNotNull2] */
@Composable
fun <T, X> StorageSetting<T>.collectAsStateNotNull(
    initialValue: T = getValueNotNull(),
    mapper: (T) -> X,
): State<X>
        /* --8<-- [end: collectAsStateNotNull2] */ {
    return flow.map { mapper(it) }.collectAsState(initial = mapper(initialValue))
}