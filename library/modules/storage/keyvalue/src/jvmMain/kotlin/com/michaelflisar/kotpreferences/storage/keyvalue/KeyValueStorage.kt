package com.michaelflisar.kotpreferences.storage.keyvalue

import com.michaelflisar.kotpreferences.core.classes.BaseStorage
import com.michaelflisar.kotpreferences.core.classes.StorageDataType
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import java.io.File
import kotlinx.io.files.Path

fun KeyValueStorage.Companion.create(
    folder: File,
    fileName: String = "settings.txt",
    delimiter: String = "=",
    encryption: StorageEncryption? = null,
    cache: Boolean = true,
) = KeyValueStorage(
    Path(File(folder, fileName).absolutePath),
    delimiter,
    encryption,
    cache,
)