package com.michaelflisar.kotpreferences.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.michaelflisar.kotpreferences.compose.collectAsState
import com.michaelflisar.kotpreferences.compose.collectAsStateNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun main() = application {

    val scope = rememberCoroutineScope()
    val counter by DemoSettingsModel.counter.collectAsStateNotNull()

    Window(
        title = "KotPreferences Demo",
        onCloseRequest = ::exitApplication
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Preference file folder path:", fontWeight = FontWeight.Bold)
                Text(System.getProperty("user.dir"))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Counter: $counter (this counter is saved inside the preference file)")
            Button(onClick = {
                scope.launch(Dispatchers.IO) {
                    DemoSettingsModel.counter.update(counter + 1)
                }
            }) {
                Text("Increase Counter")
            }
        }

    }
}