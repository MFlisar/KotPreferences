package com.michaelflisar.kotpreferences.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting

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