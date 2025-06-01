package com.michaelflisar.lumberjack.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.CanvasBasedWindow
import com.michaelflisar.kotpreferences.compose.asMutableStateNotNull
import com.michaelflisar.kotpreferences.core.SettingsModel
import com.michaelflisar.kotpreferences.storage.keyvalue.LocalStorageKeyValueStorage

/*
 * LocalStorageKeyValueStorage will save the settings in the browser's local storage
 */
object Prefs : SettingsModel(
    LocalStorageKeyValueStorage.create(
        key = "prefs"
    )
) {
    val counter by intPref(0)
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
suspend fun main() {

    CanvasBasedWindow("Demo", canvasElementId = "ComposeTarget") {
        val counter = Prefs.counter.asMutableStateNotNull()

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
                Column(
                    modifier = Modifier.padding(paddingValues).padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Button(onClick = {
                        counter.value += 1
                    }) {
                        Text("Counter: ${counter.value}")
                    }
                }
            }
        }
    }
}