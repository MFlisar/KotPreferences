package com.michaelflisar.kotpreferences.storage.keyvalue

import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption
import com.michaelflisar.kotpreferences.storage.keyvalue.setup.KeyValueConverter
import com.michaelflisar.kotpreferences.storage.keyvalue.setup.KeyValueStorageDefaults
import kotlinx.io.files.Path

fun KeyValueStorage.Companion.create(
    filePath: Path,
    converter: KeyValueConverter = KeyValueStorageDefaults.CONVERTER,
    encryption: StorageEncryption? = null,
    cache: Boolean = true,
) = KeyValueStorage(
    exists = { FileUtil.exists(filePath) },
    read = {
        val content = FileUtil.readFile(filePath)
         converter.readFromString(content)
    },
    write = {
        val content = converter.writeToString(it)
        FileUtil.writeFile(filePath, content)
    },
    encryption = encryption,
    cache = cache,
)