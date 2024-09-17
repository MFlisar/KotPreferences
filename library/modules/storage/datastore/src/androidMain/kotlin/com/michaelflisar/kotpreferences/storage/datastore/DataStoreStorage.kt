package com.michaelflisar.kotpreferences.storage.datastore

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption
import com.michaelflisar.kotpreferences.core.initialisation.SettingSetup
import okio.Path.Companion.toPath

fun DataStoreStorage.Companion.create(
    name: String = "settings",
    encryption: StorageEncryption? = null,
    cache: Boolean = true
) = DataStoreStorage(
    dataStore = PreferenceDataStoreFactory.createWithPath(produceFile = {
        SettingSetup.context.filesDir.resolve("$name.preferences_pb").absolutePath.toPath()
    }),
    encryption = encryption,
    cache = cache
)