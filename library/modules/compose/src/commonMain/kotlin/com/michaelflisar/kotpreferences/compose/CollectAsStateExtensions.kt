package com.michaelflisar.kotpreferences.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
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
    return flow.map { mapper(it) }.collectAsState(initial = initialValue?.let { mapper(it) })
}

// ------------------------
// not nullable versions
// ------------------------

@OptIn(InternalApi::class)
/* --8<-- [start: collectAsStateNotNull1] */
@Composable
fun <T> StorageSetting<T>.collectAsStateNotNull(
    initialValue: T = getValueNotNull()
): State<T>
/* end-snippet */ {
    return flow.collectAsState(initial = initialValue)
}

@OptIn(InternalApi::class)
/* --8<-- [start: collectAsStateNotNull2] */
@Composable
fun <T, X> StorageSetting<T>.collectAsStateNotNull(
    initialValue: T = getValueNotNull(),
    mapper: (T) -> X,
): State<X>
/* end-snippet */ {
    return flow.map { mapper(it) }.collectAsState(initial = mapper(initialValue))
}









