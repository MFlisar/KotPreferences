package com.michaelflisar.kotpreferences.storage.datastore

import com.michaelflisar.kotpreferences.core.interfaces.Storage
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption

@Deprecated("Use DataStoreStorage.create(...) instead", ReplaceWith("DataStoreStorage.create(name, encryption, cache)"))
expect fun createDataStoreStorage(
    name: String = "settings",
    encryption: StorageEncryption? = null,
    cache: Boolean = true
): Storage

expect fun DataStoreStorage.Companion.create(
    name: String = "settings",
    encryption: StorageEncryption? = null,
    cache: Boolean = true
): Storage

expect fun DataStoreStorage.Companion.create(
    folder: String,
    name: String = "settings",
    encryption: StorageEncryption? = null,
    cache: Boolean = true
): Storage