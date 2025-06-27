package com.michaelflisar.kotpreferences.storage.datastore

import com.michaelflisar.kotpreferences.core.interfaces.Storage
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption
import java.io.File

actual fun createDataStoreStorage(
    name: String,
    encryption: StorageEncryption?,
    cache: Boolean
): Storage {
    val appFolder = File(System.getProperty("user.dir"))
    return DataStoreStorage.create(
        folder = appFolder,
        name = name,
        encryption = encryption,
        cache = cache
    )
}