package com.michaelflisar.kotpreferences.storage.datastore

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.michaelflisar.kotpreferences.core.interfaces.Storage
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual fun createDataStoreStorage(
    name: String,
    encryption: StorageEncryption?,
    cache: Boolean
): Storage {
    return DataStoreStorage(
        dataStore = PreferenceDataStoreFactory.createWithPath(
            produceFile = {
                NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null
                ).let {
                    requireNotNull(it).path + "/$name.preferences_pb"
                }.toPath()
            }
        ),
        encryption = encryption,
        cache = cache
    )
}

@OptIn(ExperimentalForeignApi::class)
actual fun DataStoreStorage.Companion.create(
    name: String,
    encryption: StorageEncryption?,
    cache: Boolean
): Storage {
    return DataStoreStorage(
        dataStore = PreferenceDataStoreFactory.createWithPath(
            produceFile = {
                NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null
                ).let {
                    requireNotNull(it).path + "/$name.preferences_pb"
                }.toPath()
            }
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
    return DataStoreStorage(
        dataStore = PreferenceDataStoreFactory.createWithPath(
            produceFile = {
                "$folder/$name.preferences_pb".toPath()
            }
        ),
        encryption = encryption,
        cache = cache
    )
}