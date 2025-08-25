package com.michaelflisar.kotpreferences.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlin.coroutines.CoroutineContext

actual val StorageContext: CoroutineContext = Dispatchers.IO