package com.michaelflisar.kotpreferences.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.michaelflisar.kotpreferences.demo.classes.DemoUtil
import com.michaelflisar.kotpreferences.demo.composables.MyLogInfo
import com.michaelflisar.kotpreferences.demo.composables.MyRegionTitle
import com.michaelflisar.kotpreferences.demo.settings.DemoSettingsModel
import com.michaelflisar.kotpreferences.demo.settings.TestEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DemoActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val initialTestsDone = rememberSaveable { mutableStateOf(false) }
            val logs =
                rememberSaveable(saver = listSaverKeepEntryStateList()) { mutableStateListOf<String>() }

            LaunchedEffect(initialTestsDone.value) {
                if (!initialTestsDone.value) {
                    runTests(logs)
                    initialTestsDone.value = true
                }
            }

            MaterialTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.app_name)) },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                ) { paddingValues ->
                    Content(paddingValues, logs)
                }
            }
        }
    }

    // ----------------
    // Content
    // ----------------

    @Composable
    fun Content(
        paddingValues: PaddingValues,
        logs: MutableList<String>
    ) {

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // --------------------
            // Logs
            // --------------------

            val listState = rememberLazyListState()
            val lastScroll = rememberSaveable { mutableIntStateOf(logs.size) }

            // always scroll to last item (but don't repeat this on screen rotation/recreation)
            LaunchedEffect(logs.size) {
                if (logs.size > 0 && lastScroll.value != logs.size - 1) {
                    listState.scrollToItem(logs.size - 1)
                    println("logs.size = scrolling...")
                    lastScroll.value = logs.size - 1
                }
            }

            MyRegionTitle("Logs")
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f)
            ) {
                logs.forEachIndexed { index, log ->
                    item {
                        if (log.isEmpty()) {
                            HorizontalDivider()
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

    // ----------------
    // helper functions
    // ----------------


    fun <Original, Saveable> listSaverKeepEmpty(
        save: SaverScope.(value: Original) -> List<Saveable>,
        restore: (list: List<Saveable>) -> Original?
    ): Saver<Original, Any> = @Suppress("UNCHECKED_CAST") (Saver(
        save = {
            val list = save(it)
            for (index in list.indices) {
                val item = list[index]
                if (item != null) {
                    require(canBeSaved(item))
                }
            }
            ArrayList(list)
        },
        restore = restore as (Any) -> Original?
    )
            )

    fun <T : Any> listSaverKeepEntryStateList() = listSaverKeepEmpty<SnapshotStateList<T>, T>(
        save = { stateList ->
            if (stateList.isNotEmpty()) {
                val first = stateList.first()
                if (!canBeSaved(first)) {
                    throw IllegalStateException("${first::class} cannot be saved. By default only types which can be stored in the Bundle class can be saved.")
                }
                stateList.toList()
            } else {
                emptyList()
            }
        },
        restore = {
            it.toMutableStateList()
        }
    )
}