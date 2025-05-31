package com.michaelflisar.kotpreferences.storage.keyvalue

import com.michaelflisar.kotpreferences.core.initialisation.SettingSetup
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption
import com.michaelflisar.kotpreferences.storage.keyvalue.setup.KeyValueConverter
import com.michaelflisar.kotpreferences.storage.keyvalue.setup.KeyValueStorageDefaults
import kotlinx.io.files.Path

fun KeyValueStorage.Companion.create(
    fileName: String = "settings.txt",
    converter: KeyValueConverter = KeyValueStorageDefaults.CONVERTER,
    encryption: StorageEncryption? = null,
    cache: Boolean = true
) = KeyValueStorage.create(
    filePath = Path(SettingSetup.context.filesDir.resolve(fileName).absolutePath),
    converter = converter,
    encryption = encryption,
    cache = cache,
)