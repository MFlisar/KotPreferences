package com.michaelflisar.kotpreferences.storage.datastore

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.michaelflisar.kotpreferences.core.initialisation.SettingSetup
import com.michaelflisar.kotpreferences.core.interfaces.Storage
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption
import okio.Path.Companion.toOkioPath
import java.io.File

actual fun createDataStoreStorage(
    name: String,
    encryption: StorageEncryption?,
    cache: Boolean
): Storage {
    return DataStoreStorage(
        dataStore = PreferenceDataStoreFactory.createWithPath(produceFile = {
            SettingSetup.context.filesDir.resolve("$name.preferences_pb").toOkioPath()
        }),
        encryption = encryption,
        cache = cache
    )
}

actual fun DataStoreStorage.Companion.create(
    name: String,
    encryption: StorageEncryption?,
    cache: Boolean
): Storage {
    return DataStoreStorage(
        dataStore = PreferenceDataStoreFactory.createWithPath(produceFile = {
            SettingSetup.context.filesDir.resolve("$name.preferences_pb").toOkioPath()
        }),
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
        dataStore = PreferenceDataStoreFactory.createWithPath(produceFile = {
            File(folder).resolve("$name.preferences_pb").toOkioPath()
        }),
        encryption = encryption,
        cache = cache
    )
}