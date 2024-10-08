package com.michaelflisar.kotpreferences.storage.datastore

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption
import okio.Path.Companion.toPath
import java.io.File

fun DataStoreStorage.Companion.create(
    folder: File,
    name: String = "settings",
    encryption: StorageEncryption? = null,
    cache: Boolean = true
) = DataStoreStorage(
    dataStore = PreferenceDataStoreFactory.createWithPath(
        produceFile = { File(folder, "$name.preferences_pb").absolutePath.toPath() }
    ),
    encryption = encryption,
    cache = cache
)