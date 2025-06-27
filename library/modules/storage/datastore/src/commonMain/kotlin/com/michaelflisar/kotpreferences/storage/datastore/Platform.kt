package com.michaelflisar.kotpreferences.storage.datastore

import com.michaelflisar.kotpreferences.core.interfaces.Storage
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption

expect fun createDataStoreStorage(
    name: String = "settings",
    encryption: StorageEncryption? = null,
    cache: Boolean = true
): Storage