package com.michaelflisar.kotpreferences.demo

import com.michaelflisar.kotpreferences.core.SettingsModel
import com.michaelflisar.kotpreferences.storage.datastore.DataStoreStorage
import com.michaelflisar.kotpreferences.storage.datastore.create
import java.io.File

object DemoSettingsModel : SettingsModel(
    DataStoreStorage.create(
        folder = File(System.getProperty("user.dir")),
        name = "settings"
    )
) {

    val counter by intPref(0)

}