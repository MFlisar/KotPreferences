package com.michaelflisar.kotpreferences.demo

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.michaelflisar.kotpreferences.storage.datastore.DataStoreStorage
import com.michaelflisar.kotpreferences.storage.datastore.create
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

val SettingsModel = DemoSettingsModel(
    DataStoreStorage.create(
        name = "demo_settings"
    )
)

/**
 * iOS entry point used by the Xcode demo project (demo/xcode).
 */
fun MainViewController() = ComposeUIViewController {
    MaterialTheme {
        Scaffold { paddingValues ->
            DemoContent(
                modifier = Modifier.padding(paddingValues),
                platform = "iOS",
                ioContext = Dispatchers.IO,
                settings = listOf(
                    "DataStore" to SettingsModel
                )
            )
        }
    }
}
