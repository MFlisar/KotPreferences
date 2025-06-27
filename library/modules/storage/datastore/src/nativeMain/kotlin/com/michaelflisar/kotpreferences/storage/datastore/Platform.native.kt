package com.michaelflisar.kotpreferences.storage.datastore

import com.michaelflisar.kotpreferences.core.interfaces.Storage
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption

actual fun createDataStoreStorage(
    name: String,
    encryption: StorageEncryption?,
    cache: Boolean
): Storage {
    return DataStoreStorage.create(
        name = name,
        encryption = encryption,
        cache = cache
    )
}