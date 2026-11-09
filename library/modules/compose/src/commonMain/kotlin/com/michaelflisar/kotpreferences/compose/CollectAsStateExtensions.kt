package com.michaelflisar.kotpreferences.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.michaelflisar.kotpreferences.core.InternalApi
import com.michaelflisar.kotpreferences.core.getValueNotNull
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// ------------------------
// nullable versions
// ------------------------

/* begin-snippet: CollectAsStateExtensions::collectAsState1 */
@Composable
fun <T> StorageSetting<T>.collectAsState(
    initialValue: T? = getCachedValue()
): State<T?>
/* end-snippet */ {
    return flow.collectAsState(initial = initialValue)
}

/* begin-snippet: CollectAsStateExtensions::collectAsState2 */
@Composable
fun <T, X> StorageSetting<T>.collectAsState(
    initialValue: T? = getCachedValue(),
    mapper: (T) -> X,
): State<X?>
/* end-snippet */ {
    val mappedFlow = remember(flow, mapper) { flow.map(mapper) }
    return mappedFlow.collectAsState(initial = initialValue?.let(mapper))
}

// ------------------------
// not nullable versions
// ------------------------

@OptIn(InternalApi::class)
/* begin-snippet: CollectAsStateExtensions::collectAsStateNotNull1 */
@Composable
fun <T> StorageSetting<T>.collectAsStateNotNull(
    initialValue: T = getValueNotNull()
): State<T>
/* end-snippet */ {
    return flow.collectAsState(initial = initialValue)
}

@OptIn(InternalApi::class)
/* begin-snippet: CollectAsStateExtensions::collectAsStateNotNull2 */
@Composable
fun <T, X> StorageSetting<T>.collectAsStateNotNull(
    initialValue: T = getValueNotNull(),
    mapper: (T) -> X,
): State<X>
/* end-snippet */ {
    val mappedFlow = remember(flow, mapper) { flow.map(mapper) }
    return mappedFlow.collectAsState(initial = mapper(initialValue))
}








