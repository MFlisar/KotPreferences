package com.michaelflisar.kotpreferences.compose

import com.michaelflisar.kotpreferences.core.InternalApi
import com.michaelflisar.kotpreferences.core.getValueNotNull
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@OptIn(InternalApi::class)
        /* --8<-- [start: asStateFlow1] */
fun <T> StorageSetting<T>.asStateFlow(
    scope: CoroutineScope,
    apply: (flow: Flow<T>) -> Flow<T> = { it },
    initialValue: T = getValueNotNull(),
): StateFlow<T>
        /* --8<-- [end: asStateFlow1] */ {
    return apply(flow).stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = initialValue
    )
}

@OptIn(InternalApi::class)
        /* --8<-- [start: asStateFlow2] */
fun <T, T2> StorageSetting<T>.asStateFlowMapped(
    scope: CoroutineScope,
    map: (T) -> T2,
    initialValue: T = getValueNotNull(),
): StateFlow<T2>
        /* --8<-- [end: asStateFlow2] */ {
    return flow.map { map(it) }.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = map(initialValue)
    )
}
