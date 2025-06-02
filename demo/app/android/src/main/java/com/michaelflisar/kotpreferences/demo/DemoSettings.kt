package com.michaelflisar.kotpreferences.demo

import com.michaelflisar.kotpreferences.core.initialisation.SettingSetup
import com.michaelflisar.kotpreferences.demo.storages.DemoEncryptedStorage
import com.michaelflisar.kotpreferences.storage.datastore.DataStoreStorage
import com.michaelflisar.kotpreferences.storage.datastore.create

object DemoSettings {

    val SettingsModel = DemoSettingsModel(
        DataStoreStorage.create(
            name = "demo_settings",
            cache = SettingSetup.ENABLE_CACHING, // false by default, only relevant for blocking reads
            //encryption = DemoStorageEncryption
        )
    )

    val EncryptedSettingsModel = DemoSettingsModel(
        DemoEncryptedStorage.create()
    )
}