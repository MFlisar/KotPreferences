package com.michaelflisar.kotpreferences.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.Modifier
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme()
            ) {
                Scaffold { paddingValues ->
                    DemoContent(
                        modifier = Modifier.padding(paddingValues),
                        platform = "Android",
                        ioContext = Dispatchers.IO,
                        settings = listOf(
                            "DataStore" to DemoSettings.SettingsModel,
                            "DataStore + Encryption" to DemoSettings.EncryptedSettingsModel
                        )
                    )
                }
            }
        }
    }
}
