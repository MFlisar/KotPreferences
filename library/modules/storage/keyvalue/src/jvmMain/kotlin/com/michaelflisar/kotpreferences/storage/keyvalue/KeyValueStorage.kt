package com.michaelflisar.kotpreferences.storage.keyvalue

import com.michaelflisar.kotpreferences.core.classes.BaseStorage
import com.michaelflisar.kotpreferences.core.classes.StorageDataType
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okio.Path
import okio.Path.Companion.toPath
import java.io.File

fun KeyValueStorage.Companion.create(
    folder: File,
    fileName: String = "settings.txt",
    delimiter: String = "=",
    encryption: StorageEncryption? = null,
    cache: Boolean = true,
) = KeyValueStorage(
    File(folder, fileName).absolutePath.toPath(),
    delimiter,
    encryption,
    cache,
)