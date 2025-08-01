package com.michaelflisar.kotpreferences.storage.datastore

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
fun DataStoreStorage.Companion.create(
    name: String = "settings",
    cache: Boolean = true,
    encryption: StorageEncryption? = null
) = DataStoreStorage(
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

@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
fun DataStoreStorage.Companion.create(
    folder: String,
    name: String = "settings",
    cache: Boolean = true,
    encryption: StorageEncryption? = null
) = DataStoreStorage(
    dataStore = PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            "$folder/$name.preferences_pb".toPath()
        }
    ),
    encryption = encryption,
    cache = cache
)