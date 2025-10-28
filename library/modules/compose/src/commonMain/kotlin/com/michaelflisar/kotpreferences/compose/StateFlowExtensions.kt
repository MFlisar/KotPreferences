package com.michaelflisar.kotpreferences.compose

import com.michaelflisar.kotpreferences.core.InternalApi
import com.michaelflisar.kotpreferences.core.getValueNotNull
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import com.michaelflisar.kotpreferences.core.tryGetValueNotNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

// ------------------------
// nullable versions
// ------------------------

@OptIn(InternalApi::class)
/* --8<-- [start: asStateFlow1] */
fun <T> StorageSetting<T>.asStateFlow(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(5_000)
): StateFlow<T?>
/* --8<-- [end: asStateFlow1] */ {
    return flow.stateIn(
        scope = scope,
        started = started,
        initialValue = tryGetValueNotNull()
    )
}

@OptIn(InternalApi::class)
/* --8<-- [start: asStateFlow2] */
fun <T, T2> StorageSetting<T>.asStateFlow(
    scope: CoroutineScope,
    mapper: (T?) -> T2?,
    started: SharingStarted = SharingStarted.WhileSubscribed(5_000),
): StateFlow<T2?>
/* --8<-- [end: asStateFlow2] */ {
    return flow.map { mapper(it) }.stateIn(
        scope = scope,
        started = started,
        initialValue = mapper(tryGetValueNotNull())
    )
}

// ------------------------
// not nullable versions
// ------------------------

@OptIn(InternalApi::class)
/* --8<-- [start: asStateFlowNotNull1] */
fun <T> StorageSetting<T>.asStateFlowNotNull(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(5_000)
): StateFlow<T>
/* --8<-- [end: asStateFlowNotNull1] */ {
    return flow.stateIn(
        scope = scope,
        started = started,
        initialValue = getValueNotNull()
    )
}

@OptIn(InternalApi::class)
/* --8<-- [start: asStateFlowNotNull2] */
fun <T, T2> StorageSetting<T>.asStateFlowNotNull(
    scope: CoroutineScope,
    mapper: (T) -> T2,
    started: SharingStarted = SharingStarted.WhileSubscribed(5_000),
): StateFlow<T2>
/* --8<-- [end: asStateFlowNotNull2] */ {
    return flow.map { mapper(it) }.stateIn(
        scope = scope,
        started = started,
        initialValue = mapper(getValueNotNull())
    )
}