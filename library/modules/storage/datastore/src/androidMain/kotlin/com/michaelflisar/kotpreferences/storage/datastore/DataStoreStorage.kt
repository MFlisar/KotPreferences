package com.michaelflisar.kotpreferences.storage.datastore

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.michaelflisar.kotpreferences.core.initialisation.SettingSetup
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption
import okio.Path.Companion.toOkioPath

fun DataStoreStorage.Companion.create(
    name: String = "settings",
    encryption: StorageEncryption? = null,
    cache: Boolean = true
) = DataStoreStorage(
    dataStore = PreferenceDataStoreFactory.createWithPath(produceFile = {
        SettingSetup.context.filesDir.resolve("$name.preferences_pb").toOkioPath()
    }),
    encryption = encryption,
    cache = cache
)