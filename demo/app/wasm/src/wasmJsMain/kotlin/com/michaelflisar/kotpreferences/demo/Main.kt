package com.michaelflisar.kotpreferences.demo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import com.michaelflisar.kotpreferences.storage.keyvalue.LocalStorageKeyValueStorage
import kotlinx.browser.document
import kotlinx.coroutines.Dispatchers

/*
 * LocalStorageKeyValueStorage will save the settings in the browser's local storage
 */
val storage = LocalStorageKeyValueStorage.create(key = "prefs")
val settingsModel = DemoSettingsModel(storage)

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
fun main() {
    ComposeViewport(
        viewportContainer = document.body!!//getElementById("ComposeTarget")!!
    ) {
        MaterialTheme {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text("Demo", modifier = Modifier.padding(8.dp))
                        }
                    )
                }
            ) { paddingValues ->
                DemoContent(
                    modifier = Modifier.padding(paddingValues).fillMaxSize(),
                    platform = "WASM",
                    ioContext = Dispatchers.Default,
                    settings = listOf(
                        "LocalStorage" to settingsModel
                    )
                )
            }
        }
    }
}