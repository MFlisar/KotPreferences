package com.michaelflisar.kotpreferences.core

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

actual val StorageContext: CoroutineContext = Dispatchers.Default