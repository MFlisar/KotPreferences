package com.michaelflisar.kotpreferences.test

import com.michaelflisar.kotpreferences.storage.keyvalue.KeyValue
import com.michaelflisar.kotpreferences.storage.keyvalue.KeyValueStorage
import com.michaelflisar.kotpreferences.storage.keyvalue.create
import com.michaelflisar.kotpreferences.storage.keyvalue.setup.KeyValueBlockInset
import com.michaelflisar.kotpreferences.storage.keyvalue.setup.KeyValueConverter
import com.michaelflisar.kotpreferences.storage.keyvalue.setup.KeyValueFixedInset
import com.michaelflisar.kotpreferences.storage.keyvalue.setup.KeyValueInset
import kotlinx.coroutines.test.runTest
import kotlinx.io.files.Path
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestKeyValueStore {

    @Test
    fun `writes and reads simple single-line key-value`() = runTest {
        val input = listOf(KeyValue("username", "admin"), KeyValue("theme", "dark"))
        val storage = createStorage()
        val result = saveAndReadBack(input, storage)

        assertEquals(input, result)
    }

    @Test
    fun `writes and reads multiline value with block inset`() = runTest {
        val input = listOf(KeyValue("description", "This is a config\nspanning multiple\nlines"))
        val storage = createStorage(inset = KeyValueBlockInset(' '))
        val result = saveAndReadBack(input, storage)

        assertEquals(1, result.size)
        assertEquals(input[0].key, result[0].key)
        assertEquals(input[0].value, result[0].value)
    }

    @Test
    fun `writes and reads multiline value with fixed inset`() = runTest {
        val input = listOf(
            KeyValue("note", "Line1\nLine2\nLine3")
        )
        val storage = createStorage(inset = KeyValueFixedInset("    "))// 4 spaces
        val result = saveAndReadBack(input, storage)

        assertEquals(input[0].value, result[0].value)
    }

    @Test
    fun `supports empty values`() = runTest {
        val input = listOf(
            KeyValue("empty1", ""),
            KeyValue("empty2", "")
        )
        val storage = createStorage()
        val result = saveAndReadBack(input, storage)

        assertEquals(2, result.size)
        assertTrue(result.all { it.value.isEmpty() })
    }

    @Test
    fun `preserves ordering and whitespace`() = runTest {
        val input = listOf(
            KeyValue("a", "first"),
            KeyValue("b", "second line\nwith more"),
            KeyValue("c", "")
        )
        val storage = createStorage()
        val result = saveAndReadBack(input, storage)

        assertEquals(input, result)
    }

    @Test
    fun `fails on keys with whitespace`() = runTest {
        val content = "bad key=value\n"
        val converter = KeyValueConverter(
            separator = "=",
            inset = KeyValueBlockInset(' ')
        )
        val result = runCatching {
            val storage = KeyValueStorage(
                exists = { true },
                read = {
                    converter.readFromString(content)
                },
                write = {
                    // ---
                }
            )
            storage.read()
        }
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
        assertTrue(result.exceptionOrNull()?.message!!.contains("Keys must not contain whitespace"))
    }

    // ------------------
    // Helper methods
    // ------------------

    private fun createStorage(
        inset: KeyValueInset = KeyValueBlockInset(),
        separator: String = "=",
    ): KeyValueStorage {
        val folder = File(System.getProperty("user.dir"))
        val fileName = "test.txt"
        val file = File(folder, fileName)
        if (file.exists())
            file.delete()
        val storage = KeyValueStorage.create(
            filePath = Path(file.absolutePath),
            converter = KeyValueConverter(
                separator = separator,
                inset = inset
            )
        )
        return storage
    }

    private suspend fun saveAndReadBack(
        input: List<KeyValue>,
        storage: KeyValueStorage,
    ): List<KeyValue> {
        storage.write(input)
        return storage.read()
    }
}