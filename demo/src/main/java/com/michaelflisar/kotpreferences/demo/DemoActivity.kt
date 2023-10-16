package com.michaelflisar.kotpreferences.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.michaelflisar.kotpreferences.compose.collectAsState
import com.michaelflisar.kotpreferences.demo.classes.DemoTheme
import com.michaelflisar.kotpreferences.demo.classes.DemoUtil
import com.michaelflisar.kotpreferences.demo.composables.MyInfoLine
import com.michaelflisar.kotpreferences.demo.composables.MyLogInfo
import com.michaelflisar.kotpreferences.demo.composables.MyRegionTitle
import com.michaelflisar.kotpreferences.demo.settings.DemoSettingsModel
import com.michaelflisar.kotpreferences.demo.settings.TestEnum
import com.michaelflisar.kotpreferences.demo.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DemoActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val theme = DemoSettingsModel.appTheme.collectAsState()
            val dynamicTheme = DemoSettingsModel.dynamicTheme.collectAsState()

            val initialTestsDone = rememberSaveable {
                mutableStateOf(false)
            }

            val logs = rememberSaveable(saver = DemoUtil.LIST_SAVER) {
                mutableStateListOf()
            }

            LaunchedEffect(initialTestsDone.value) {
                if (!initialTestsDone.value) {
                    runTests(logs)
                    initialTestsDone.value = true
                }
            }

            AppTheme(
                darkTheme = theme.value.isDark(),
                dynamicColor = dynamicTheme.value
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {

                        TopAppBar(
                            title = { Text(stringResource(R.string.app_name)) },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Content(logs)
                        }
                    }
                }
            }
        }
    }

    // ----------------
    // Content
    // ----------------

    @Composable
    fun ColumnScope.Content(logs: MutableList<String>) {

        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {

            // --------------------
            // Infos about current state
            // --------------------

            val theme = DemoSettingsModel.appTheme.collectAsState()
            val dynamicTheme = DemoSettingsModel.dynamicTheme.collectAsState()
            MyRegionTitle("Infos")
            MyInfoLine("Current Theme", theme.value.name)
            MyInfoLine("Dynamic Theme", if (dynamicTheme.value) "YES" else "NO")

            // --------------------
            // Actions
            // --------------------

            MyRegionTitle("Actions")
            Button(onClick = {
                scope.launch(Dispatchers.IO) {
                    val value = DemoSettingsModel.appTheme.read()
                    val next = DemoTheme.values()[(value.ordinal + 1) % DemoTheme.values().size]
                    DemoSettingsModel.appTheme.update(next)
                    logs.add("DemoTheme changed from $value to $next")
                }
            }) {
                Text("Next Theme")
            }
            Button(onClick = {
                scope.launch(Dispatchers.IO) {
                    val value = DemoSettingsModel.dynamicTheme.read()
                    val next = !value
                    DemoSettingsModel.dynamicTheme.update(next)
                    logs.add("DynamicTheme changed from $value to $next")
                }
            }) {
                Text("Toggle dynamic theme")
            }
            Button(onClick = {
                scope.launch { runTests(logs) }
            }) {
                Text("Rerun tests")
            }
        }

        // --------------------
        // Logs
        // --------------------

        val listState = rememberLazyListState()

        val lastScroll = rememberSaveable {
            mutableStateOf(logs.size)
        }

        // always scroll to last item (but don't repeat this on screen rotation/recreation)
        LaunchedEffect(logs.size) {
            if (logs.size > 0 && lastScroll.value != logs.size - 1) {
                listState.scrollToItem(logs.size - 1)
                println("logs.size = scrolling...")
                lastScroll.value = logs.size - 1
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            MyRegionTitle("Logs")
            LazyColumn(
                state = listState
            ) {
                logs.forEachIndexed { index, log ->
                    item {
                        if (log.isEmpty()) {
                            Divider()
                        } else {
                            MyLogInfo("Log $index", log)
                        }
                    }
                }
            }
        }
    }

    // ----------------
    // Tests
    // ----------------

    private suspend fun addLogLine(logs: MutableList<String>, line: String) {
        withContext(Dispatchers.Main) {
            logs.add(line)
        }
    }

    private suspend fun runTests(logs: MutableList<String>) {

        withContext(Dispatchers.IO) {

            // ---------------
            // string value test
            // ---------------

            val testString = DemoSettingsModel.testString.read()
            addLogLine(logs, "'testString' before update = $testString")
            val testStringUpdated = DemoUtil.randomString(10)

            DemoSettingsModel.testString.update(testStringUpdated)
            addLogLine(logs, "'testString' changed to = $testStringUpdated")

            val testString2 = DemoSettingsModel.testString.read()
            addLogLine(logs, "'testString' after update = $testString2")

            // ---------------
            // int value test (same as bool/float/long)
            // ---------------

            addLogLine(logs, "")

            val testInt1 = DemoSettingsModel.testInt.read()
            addLogLine(logs, "'testInt' before update = $testInt1")
            val testIntUpdated = testInt1 + 1

            DemoSettingsModel.testInt.update(testIntUpdated)
            addLogLine(logs, "'testInt' changed to = $testIntUpdated")

            val testInt2 = DemoSettingsModel.testInt.read()
            addLogLine(logs, "'testInt' after update = $testInt2")

            // ---------------
            // enum value test
            // ---------------

            addLogLine(logs, "")

            val testEnum1 = DemoSettingsModel.testEnum.read()
            addLogLine(logs, "'testEnum' before update = $testEnum1")
            val testEnumUpdated =
                TestEnum.values()[(testEnum1.ordinal + 1) % TestEnum.values().size]

            DemoSettingsModel.testEnum.update(testEnumUpdated)
            addLogLine(logs, "'testEnum' changed to = $testEnumUpdated")

            val testEnum2 = DemoSettingsModel.testEnum.read()
            addLogLine(logs, "'testEnum' after update = $testEnum2")

            // ---------------
            // custom serialisable class
            // ---------------

            addLogLine(logs, "")

            val testClass1 = DemoSettingsModel.testClass.read()
            addLogLine(logs, "'testClass1' before update = $testClass1")
            val testClassUpdated = testClass1.copy(
                id = testClass1.id + 1,
                name = DemoUtil.randomString(5)
            )

            DemoSettingsModel.testClass.update(testClassUpdated)
            addLogLine(logs, "'testClass' changed to = $testClassUpdated")

            val testClass2 = DemoSettingsModel.testClass.read()
            addLogLine(logs, "'testClass' after update = $testClass2")
        }
    }
}