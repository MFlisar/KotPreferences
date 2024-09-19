package com.michaelflisar.kotpreferences.demo

import com.michaelflisar.kotpreferences.core.SettingsModel
import com.michaelflisar.kotpreferences.storage.keyvalue.KeyValueStorage
import com.michaelflisar.kotpreferences.storage.keyvalue.create
import java.io.File

object KeyValueModel : SettingsModel(
    KeyValueStorage.create(
        folder = File(System.getProperty("user.dir")),
        fileName = "data.txt"
    )
) {
    val counter by intPref(0)

    val string by nullableStringPref("")
    val intSet by intSetPref()
}