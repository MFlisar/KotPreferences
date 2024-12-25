package com.michaelflisar.kotpreferences.storage.keyvalue

import com.michaelflisar.kotpreferences.core.initialisation.SettingSetup
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption
import kotlinx.io.files.Path

fun KeyValueStorage.Companion.create(
    fileName: String = "settings.txt",
    delimiter: String = "=",
    encryption: StorageEncryption? = null,
    cache: Boolean = true
) = KeyValueStorage(
    Path(SettingSetup.context.filesDir.resolve(fileName).absolutePath),
    delimiter,
    encryption,
    cache,
)