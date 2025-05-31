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

    val color by longPref(0xFF000000)
    val nullableColor by nullableLongPref(0xFF000000)

}