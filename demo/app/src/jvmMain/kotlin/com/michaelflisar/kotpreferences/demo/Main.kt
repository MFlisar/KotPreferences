package com.michaelflisar.kotpreferences.demo

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.michaelflisar.kotpreferences.storage.datastore.DataStoreStorage
import com.michaelflisar.kotpreferences.storage.datastore.create
import com.michaelflisar.kotpreferences.storage.keyvalue.KeyValueStorage
import com.michaelflisar.kotpreferences.storage.keyvalue.create
import kotlinx.coroutines.Dispatchers
import java.io.File

fun main() {

    val appFolder = File(System.getProperty("user.dir"))
    val storage = DataStoreStorage.create(
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

    application {
        Window(
            title = "KotPreferences Demo",
            onCloseRequest = ::exitApplication,
            state = rememberWindowState(
                position = WindowPosition(Alignment.Center),
                width = 800.dp,
                height = 600.dp
            )
        ) {
            MaterialTheme {
                Scaffold { paddingValues ->
                    DemoContent(
                        modifier = Modifier.padding(paddingValues),
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
    }
}