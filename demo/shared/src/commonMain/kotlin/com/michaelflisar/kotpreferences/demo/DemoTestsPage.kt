package com.michaelflisar.kotpreferences.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.michaelflisar.kotpreferences.demo.classes.TestEnum
import com.michaelflisar.kotpreferences.demo.composables.TestColumn
import com.michaelflisar.kotpreferences.demo.utils.DemoUtil
import com.michaelflisar.kotpreferences.demo.utils.ListSaverUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlin.coroutines.CoroutineContext

@Serializable
sealed class Log {

    @Composable
    abstract fun Render()

    @Serializable
    class Line(
        val text: String,
        val color: Int? = null
    ) : Log() {

        @Composable
        override fun Render() {
            val color = color?.let { Color(it) } ?: Color.Unspecified
            Text(text, style = MaterialTheme.typography.bodySmall, color = color)
        }
    }

    @Serializable
    class Title(
        val text: String,
        val color: Int? = null
    ) : Log() {

        @Composable
        override fun Render() {
            val color = color?.let { Color(it) } ?: Color.Unspecified
            Text(text, style = MaterialTheme.typography.titleSmall, color = color)
        }
    }

    @Serializable
    data object Divider : Log() {
        @Composable
        override fun Render() {
            Column(
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                HorizontalDivider()
            }
        }
    }
}


@Composable
internal fun DemoTestsPage(
    modifier: Modifier,
    ioContext: CoroutineContext,
    settingsModel: DemoSettingsModel
) {
    val initialTestsDone = rememberSaveable { mutableStateOf(false) }
    val logs =
        rememberSaveable(saver = ListSaverUtil.listSaverKeepEntryStateList()) { mutableStateListOf<Log>() }

    LaunchedEffect(initialTestsDone.value) {
        if (!initialTestsDone.value) {
            runTests(settingsModel, ioContext, logs)
            initialTestsDone.value = true
        }
    }

    val listState = rememberLazyListState()
    val lastScroll = rememberSaveable { mutableIntStateOf(logs.size) }

    // always scroll to last item (but don't repeat this on screen rotation/recreation)
    LaunchedEffect(logs.size) {
        if (logs.isNotEmpty() && lastScroll.value != logs.size - 1) {
            listState.scrollToItem(logs.size - 1)
            println("logs.size = scrolling...")
            lastScroll.value = logs.size - 1
        }
    }

    TestColumn(
        modifier = modifier
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f)
        ) {
            logs.forEach { log ->
                item {
                    log.Render()
                }
            }
        }
    }

}


private suspend fun runTests(
    settingsModel: DemoSettingsModel,
    ioContext: CoroutineContext,
    logs: MutableList<Log>
) {

    withContext(ioContext) {

        // ---------------
        // string value test
        // ---------------

        addLogTitle(logs, "Test 1")

        val testString = settingsModel.testString.read()
        addLogLine(logs, "'testString' before update = $testString")
        val testStringUpdated = DemoUtil.randomString(10)

        settingsModel.testString.update(testStringUpdated)
        addLogLine(logs, "'testString' changed to = $testStringUpdated")

        val testString2 = settingsModel.testString.read()
        addLogLine(logs, "'testString' after update = $testString2")

        addLogResult(logs, testStringUpdated, testString2)
        addLogDivider(logs)

        // ---------------
        // int value test (same as bool/float/long)
        // ---------------

        addLogTitle(logs, "Test 2")

        val testInt1 = settingsModel.testInt.read()
        addLogLine(logs, "'testInt' before update = $testInt1")
        val testIntUpdated = testInt1 + 1

        settingsModel.testInt.update(testIntUpdated)
        addLogLine(logs, "'testInt' changed to = $testIntUpdated")

        val testInt2 = settingsModel.testInt.read()
        addLogLine(logs, "'testInt' after update = $testInt2")

        addLogResult(logs, testIntUpdated.toString(), testInt2.toString())
        addLogDivider(logs)

        // ---------------
        // enum value test
        // ---------------

        addLogTitle(logs, "Test 3")

        val testEnum1 = settingsModel.testEnum.read()
        addLogLine(logs, "'testEnum' before update = $testEnum1")
        val testEnumUpdated = TestEnum.entries[(testEnum1.ordinal + 1) % TestEnum.entries.size]

        settingsModel.testEnum.update(testEnumUpdated)
        addLogLine(logs, "'testEnum' changed to = $testEnumUpdated")

        val testEnum2 = settingsModel.testEnum.read()
        addLogLine(logs, "'testEnum' after update = $testEnum2")

        addLogResult(logs, testEnumUpdated.toString(), testEnum2.toString())
        addLogDivider(logs)

        // ---------------
        // custom serialisable class
        // ---------------

        addLogTitle(logs, "Test 4")

        val testClass1 = settingsModel.testClass.read()
        addLogLine(logs, "'testClass1' before update = $testClass1")
        val testClassUpdated = testClass1.copy(
            id = testClass1.id + 1,
            name = DemoUtil.randomString(5)
        )

        settingsModel.testClass.update(testClassUpdated)
        addLogLine(logs, "'testClass' changed to = $testClassUpdated")

        val testClass2 = settingsModel.testClass.read()
        addLogLine(logs, "'testClass' after update = $testClass2")

        addLogResult(logs, testClassUpdated.toString(), testClass2.toString())
    }
}

private suspend fun addLogDivider(logs: MutableList<Log>) {
    withContext(Dispatchers.Main) {
        logs.add(Log.Divider)
    }
}

private suspend fun addLogLine(logs: MutableList<Log>, line: String) {
    withContext(Dispatchers.Main) {
        logs.add(Log.Line(line))
    }
}

private suspend fun addLogTitle(logs: MutableList<Log>, title: String) {
    withContext(Dispatchers.Main) {
        logs.add(Log.Title(title))
    }
}

private suspend fun addLogResult(logs: MutableList<Log>, before: String, after: String) {
    withContext(Dispatchers.Main) {
        val success = before == after
        if (success) {
            logs.add(Log.Line("Test successful: $before == $after", Color.Green.toArgb()))
        } else {
            logs.add(Log.Line("Test failed: $before != $after", Color.Red.toArgb()))
        }
    }
}