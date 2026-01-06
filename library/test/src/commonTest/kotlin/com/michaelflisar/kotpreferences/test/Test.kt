package com.michaelflisar.kotpreferences.test

import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.test.InternalTestApi
import androidx.compose.ui.test.junit4.DesktopComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.michaelflisar.kotpreferences.compose.asMutableState
import com.michaelflisar.kotpreferences.core.SettingsModel
import com.michaelflisar.kotpreferences.core.interfaces.Storage
import com.michaelflisar.kotpreferences.storage.datastore.DataStoreStorage
import com.michaelflisar.kotpreferences.storage.datastore.create
import com.michaelflisar.kotpreferences.storage.keyvalue.KeyValueStorage
import com.michaelflisar.kotpreferences.storage.keyvalue.create
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class Test {

    val appFolder = File(System.getProperty("user.dir"))

    private val dataStoreStorage = DataStoreStorage.create(
        folder = appFolder,
        name = "test"
    )
    private val keyValueStorage = KeyValueStorage.create(
        folder = appFolder,
        fileName = "test.txt"
    )
    private val storageCompose = DataStoreStorage.create(
        folder = appFolder,
        name = "compose_test_storage"
    )

    class TestPreferences(storage: Storage) : SettingsModel(storage) {

        val int by intPref(0)
        val nullableInt by nullableIntPref()

        val intSet by intSetPref(emptySet())
        val stringSet by stringSetPref(emptySet())
    }

    @Test
    fun storeTest() = runTest {

        val prefsDataStore = TestPreferences(dataStoreStorage)
        val prefsKeyValue = TestPreferences(keyValueStorage)

        println("appFolder = $appFolder")

        listOf(prefsDataStore, prefsKeyValue).forEach { prefs ->

            prefs.int.update(2)
            assertEquals(2, prefs.int.read())
            prefs.int.update(5)
            assertEquals(5, prefs.int.read())

            prefs.nullableInt.update(2)
            assertEquals(2, prefs.nullableInt.read())
            prefs.nullableInt.update(null)
            assertEquals(null, prefs.nullableInt.read())
            prefs.nullableInt.update(5)
            assertEquals(5, prefs.nullableInt.read())

            prefs.intSet.update(setOf(1, 2, 3))
            assertEquals(setOf(1, 2, 3), prefs.intSet.read())
            prefs.intSet.update(emptySet())
            assertEquals(emptySet(), prefs.intSet.read())

            prefs.stringSet.update(setOf("a", "b"))
            assertEquals(setOf("a", "b"), prefs.stringSet.read())
            prefs.stringSet.update(emptySet())
            assertEquals(emptySet(), prefs.stringSet.read())

        }

    }


    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testGreetingComposable() = runTest {
        val prefsDataStore = TestPreferences(storageCompose)
        storageCompose.clear()

        composeTestRule.setContent {
            val int = prefsDataStore.int.asMutableState()
            BasicText("int = ${int.value}")
            val nullableInt = prefsDataStore.nullableInt.asMutableState()
            BasicText("nullableInt = ${nullableInt.value}")
        }

        composeTestRule
            .onNodeWithText("int = 0")
            .assertExists()
        composeTestRule
            .onNodeWithText("nullableInt = null")
            .assertExists()
    }

}