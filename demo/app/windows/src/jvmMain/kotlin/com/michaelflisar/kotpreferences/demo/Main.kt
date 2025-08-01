package com.michaelflisar.kotpreferences.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.michaelflisar.kotpreferences.demo.composables.TestColumn
import com.michaelflisar.kotpreferences.storage.datastore.DataStoreStorage
import com.michaelflisar.kotpreferences.storage.datastore.create
import com.michaelflisar.kotpreferences.storage.keyvalue.KeyValueStorage
import com.michaelflisar.kotpreferences.storage.keyvalue.create
import kotlinx.coroutines.Dispatchers
import java.io.File

fun main() = application {

    val appFolder = File(System.getProperty("user.dir"))
    val storage =  DataStoreStorage.create(
        folder = appFolder,
        name = "settings"
    )
    val settingsModel = DemoSettingsModel(storage)
    val settingsModel2 = DemoSettingsModel(
        KeyValueStorage.create(
            folder = appFolder,
            fileName = "data.txt"
        )
    )

    Window(
        title = "KotPreferences Demo",
        onCloseRequest = ::exitApplication
    ) {
        TestColumn {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Preference file folder path:", fontWeight = FontWeight.Bold)
                Text(appFolder.absolutePath)
            }
            DemoContent(
                modifier = Modifier.weight(1f),
                platform = "Windows",
                ioContext = Dispatchers.IO,
                settings = listOf(
                    "DataStore" to settingsModel,
                    "KeyValue" to settingsModel2
                )
            )
        }
    }
}