package com.michaelflisar.kotpreferences.storage.keyvalue

import com.michaelflisar.kotpreferences.core.initialisation.SettingSetup
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption
import okio.Path.Companion.toPath

fun KeyValueStorage.Companion.create(
    fileName: String = "settings.txt",
    delimiter: String = "=",
    encryption: StorageEncryption? = null,
    cache: Boolean = true,
) = KeyValueStorage(
    SettingSetup.context.filesDir.resolve(fileName).absolutePath.toPath(),
    delimiter,
    encryption,
    cache,
)