package com.michaelflisar.kotpreferences.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.michaelflisar.kotpreferences.compose.collectAsState
import com.michaelflisar.kotpreferences.compose.collectAsStateNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun main() = application {

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
            DataStoreDemo()

            Spacer(modifier = Modifier.height(16.dp))
            KeyValueDemo()

        }

    }
}

@Composable
private fun DataStoreDemo() {
    val scope = rememberCoroutineScope()
    val counter by DemoSettingsModel.counter.collectAsStateNotNull()

    Text("DataStore Example", style = MaterialTheme.typography.h4)
    Text("Counter: $counter (this counter is saved inside the preference file)")
    Button(onClick = {
        scope.launch(Dispatchers.IO) {
            DemoSettingsModel.counter.update(counter + 1)
        }
    }) {
        Text("Increase Counter")
    }

}

@Composable
private fun KeyValueDemo() {

    val counter by KeyValueModel.counter.collectAsState()
    val intSet by KeyValueModel.intSet.collectAsState()
    val string by KeyValueModel.string.collectAsState()

    val scope = rememberCoroutineScope()

    Text("Key Value Example", style = MaterialTheme.typography.h4)
    Text("counter = $counter")
    Text("intSet = $intSet")
    Text("string = $string")
    Button(onClick = {
        scope.launch(Dispatchers.IO) {
            KeyValueModel.counter.update((counter ?: 0) + 1)
        }
    }) {
        Text("Increase Counter")
    }
    Button(onClick = {
        scope.launch(Dispatchers.IO) {
            KeyValueModel.intSet.update((intSet ?: emptySet()) + (intSet?.maxOrNull()?.let { it + 1 } ?: 1))
        }
    }) {
        Text("Add To Int Set")
    }
    Button(onClick = {
        scope.launch(Dispatchers.IO) {
            KeyValueModel.string.update(if (string == null) "Value" else null)
        }
    }) {
        Text("Toggle string value")
    }
}