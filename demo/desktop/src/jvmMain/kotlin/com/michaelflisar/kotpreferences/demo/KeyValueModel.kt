package com.michaelflisar.kotpreferences.demo

import com.michaelflisar.kotpreferences.core.SettingsModel
import com.michaelflisar.kotpreferences.storage.keyvalue.KeyValueStorage
import okio.Path.Companion.toOkioPath
import java.io.File

object KeyValueModel : SettingsModel(
    KeyValueStorage(
        filePath = File(System.getProperty("user.dir"), "data.txt").toOkioPath()
    )
) {
    val counter by intPref(0)

    val string by nullableStringPref("")
    val intSet by intSetPref()
}