package com.michaelflisar.kotpreferences.storage.datastore

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.michaelflisar.kotpreferences.core.interfaces.Storage
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption
import okio.Path.Companion.toPath
import java.io.File

actual fun createDataStoreStorage(
    name: String,
    encryption: StorageEncryption?,
    cache: Boolean
): Storage {
    val appFolder = File(System.getProperty("user.dir"))
    return DataStoreStorage(
        dataStore = PreferenceDataStoreFactory.createWithPath(
            produceFile = { File(appFolder, "$name.preferences_pb").absolutePath.toPath() }
        ),
        encryption = encryption,
        cache = cache
    )
}

actual fun DataStoreStorage.Companion.create(
    name: String,
    encryption: StorageEncryption?,
    cache: Boolean
): Storage {
    val appFolder = File(System.getProperty("user.dir"))
    return DataStoreStorage(
        dataStore = PreferenceDataStoreFactory.createWithPath(
            produceFile = { File(appFolder, "$name.preferences_pb").absolutePath.toPath() }
        ),
        encryption = encryption,
        cache = cache
    )
}

actual fun DataStoreStorage.Companion.create(
    folder: String,
    name: String,
    encryption: StorageEncryption?,
    cache: Boolean
): Storage {
    val appFolder = File(folder)
    return DataStoreStorage(
        dataStore = PreferenceDataStoreFactory.createWithPath(
            produceFile = { File(appFolder, "$name.preferences_pb").absolutePath.toPath() }
        ),
        encryption = encryption,
        cache = cache
    )
}