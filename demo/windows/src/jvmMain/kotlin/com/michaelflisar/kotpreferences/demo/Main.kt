package com.michaelflisar.kotpreferences.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.michaelflisar.kotpreferences.compose.asMutableState
import com.michaelflisar.kotpreferences.compose.asMutableStateNotNull
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
            modifier = Modifier.padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Preference file folder path:", fontWeight = FontWeight.Bold)
                Text(System.getProperty("user.dir"))
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("DataStore Demo", style = MaterialTheme.typography.h3)
            DataStoreDemo()

            Spacer(modifier = Modifier.height(16.dp))
            Text("KeyValue Demo", style = MaterialTheme.typography.h3)
            KeyValueDemo()

        }

    }
}

@Composable
private fun DataStoreDemo() {
    val scope = rememberCoroutineScope()
    val counter by DemoSettingsModel.counter.collectAsStateNotNull()

    val color by DemoSettingsModel.color.collectAsStateNotNull()
    val colorMapped by DemoSettingsModel.color.collectAsStateNotNull(mapper = { Color(it) })
    val stateColor by DemoSettingsModel.color.asMutableStateNotNull()
    var stateColorMapped by DemoSettingsModel.color.asMutableStateNotNull(
        mapper = { Color(it) },
        unmapper = { it.toArgb().toLong() }
    )

    Text("DataStore Example", style = MaterialTheme.typography.h4)
    Text("Counter: $counter (this counter is saved inside the preference file)")
    Button(onClick = {
        scope.launch(Dispatchers.IO) {
            DemoSettingsModel.counter.update(counter + 1)
        }
    }) {
        Text("Increase Counter")
    }

    Text("Color", style = MaterialTheme.typography.h4)
    Color("color", color = Color(color))
    Color("colorMapped", color = colorMapped)
    Color("stateColor", color = Color(stateColor))
    Color("stateColorMapped", color = stateColorMapped)
    Text("Toggle color by update", style = MaterialTheme.typography.h6)
    SelectColorButtons {
        scope.launch(Dispatchers.IO) {
            DemoSettingsModel.color.update(it.toArgb().toLong())
        }
    }
    Text("Toggle color by mapped state", style = MaterialTheme.typography.h6)
    SelectColorButtons {
        scope.launch(Dispatchers.IO) {
            stateColorMapped = it
        }
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
            KeyValueModel.intSet.update(
                (intSet ?: emptySet()) + (intSet?.maxOrNull()?.let { it + 1 } ?: 1))
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

@Composable
fun Color(
    label: String,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = label)
        Spacer(modifier = Modifier.size(16.dp).background(color).clip(MaterialTheme.shapes.small))
    }
}

@Composable
fun SelectColorButtons(
    onColorSelected: (Color) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val colors = listOf(
        "Red" to Color.Red,
        "Green" to Color.Green,
        "Blue" to Color.Blue,
        "White" to Color.White,
        "Black" to Color.Black,
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        colors.forEach { color ->
            Button(onClick = {
                scope.launch(Dispatchers.IO) {
                    onColorSelected(color.second)
                }
            }) {
                Color(color.first, color.second)
            }
        }
    }
}