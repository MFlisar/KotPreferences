package com.michaelflisar.kotpreferences.demo

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.michaelflisar.kotpreferences.compose.asMutableStateNotNull
import com.michaelflisar.kotpreferences.compose.collectAsStateNotNull
import com.michaelflisar.kotpreferences.demo.composables.TestColor
import com.michaelflisar.kotpreferences.demo.composables.TestColumn
import com.michaelflisar.kotpreferences.demo.composables.TestRegionTitle
import com.michaelflisar.kotpreferences.demo.composables.TestRegionTitle2
import com.michaelflisar.kotpreferences.demo.composables.TestRow
import com.michaelflisar.kotpreferences.demo.composables.TestSelectColorButtons
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DemoSettingsPage(
    modifier: Modifier,
    platform: String,
    ioContext: CoroutineContext,
    settingsModel: DemoSettingsModel
) {
    val testDialog = remember { mutableStateOf(false) }
    TestColumn(
        modifier = modifier
    ) {
        TestRegionTitle("Settings ($platform)")
        Settings(
            settingsModel,
            Modifier.weight(1f),
            ioContext,
            testDialog
        )
    }
    if (testDialog.value) {
        Dialog(
            onDismissRequest = { testDialog.value = false }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Tests Dialog") }
                    )
                }
            ) {
                DemoTestsPage(
                    modifier = Modifier.padding(paddingValues = it).padding(16.dp),
                    ioContext = ioContext,
                    settingsModel = settingsModel
                )
            }
        }
    }
}

@Composable
private fun Settings(
    settingsModel: DemoSettingsModel,
    modifier: Modifier,
    ioContext: CoroutineContext,
    testDialog: MutableState<Boolean>,
) {
    val scope = rememberCoroutineScope()
    val counter by settingsModel.counter.collectAsStateNotNull()

    val color by settingsModel.color.collectAsStateNotNull()
    val colorMapped by settingsModel.color.collectAsStateNotNull(mapper = { Color(it) })
    val stateColor by settingsModel.color.asMutableStateNotNull()
    var stateColorMapped by settingsModel.color.asMutableStateNotNull(
        mapper = { Color(it) },
        unmapper = { it.toArgb().toLong() }
    )

    TestColumn(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        TestRow {
            IconButton(onClick = {
                scope.launch(ioContext) {
                    settingsModel.counter.update(counter - 1)
                }
            }) {
                Icon(Icons.Default.Remove, null)
            }
            Text("Counter: $counter")
            IconButton(onClick = {
                scope.launch(ioContext) {
                    settingsModel.counter.update(counter + 1)
                }
            }) {
                Icon(Icons.Default.Add, null)
            }
        }

        TestRegionTitle2("Color")
        TestColor("color", color = Color(color))
        TestColor("colorMapped", color = colorMapped)
        TestColor("stateColor", color = Color(stateColor))
        TestColor("stateColorMapped", color = stateColorMapped)
        TestRegionTitle2("Toggle color by update")
        TestSelectColorButtons {
            scope.launch(ioContext) {
                settingsModel.color.update(it.toArgb().toLong())
            }
        }
        Text("Toggle color by mapped state")
        TestSelectColorButtons {
            scope.launch(ioContext) {
                stateColorMapped = it
            }
        }

        OutlinedButton(
            onClick = {
                testDialog.value = true
            }
        ) {
            Text("Open Test Dialog and run tests")
        }
    }
}



