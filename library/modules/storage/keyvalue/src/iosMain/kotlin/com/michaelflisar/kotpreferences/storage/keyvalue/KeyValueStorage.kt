package com.michaelflisar.kotpreferences.storage.keyvalue

import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import okio.Path.Companion.toPath

fun KeyValueStorage.Companion.create(
    fileName: String = "settings.txt",
    delimiter: String = "=",
    encryption: StorageEncryption? = null,
    cache: Boolean = true
) = KeyValueStorage(
    NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    ).let {
        requireNotNull(it).path + "/$fileName"
    }.toPath(),
    delimiter,
    encryption,
    cache,
)