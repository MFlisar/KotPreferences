package com.michaelflisar.kotpreferences.storage.keyvalue

import com.michaelflisar.kotpreferences.core.classes.StorageDataType
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption

internal class KeyValueData {

    private var lines: MutableMap<String, String> = HashMap()
    private var data: MutableMap<String, KeyValueEntry<*>> = HashMap()

    fun clear() {
        lines.clear()
        data.clear()
    }

    fun hasKey(key: String) = lines.containsKey(key) || data.containsKey(key)

    fun getAllKeys() = (lines.keys + data.keys).toSet()

    fun getAllLines(encryption: StorageEncryption?): List<KeyValue> {
        return lines.map { KeyValue(it.key, it.value) } + data.mapNotNull { d ->
            val line = d.value.entryAsString(encryption)
            line?.let { KeyValue(d.key, it) }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getEntry(
        key: String,
        type: StorageDataType,
        encryption: StorageEncryption?
    ): KeyValueEntry<T>? {
        val entry = data[key] ?: lines[key]?.let { KeyValueEntry.create<T>(it, type, encryption) }
        return entry as KeyValueEntry<T>?
    }

    fun addLines(lines: List<KeyValue>) {
        this.lines += lines.map { it.key to it.value }
    }

    fun <T> addEntry(key: String, entry: KeyValueEntry<T>) {
        lines.remove(key)
        data[key] = entry
    }

    fun removeEntry(key: String) {
        lines.remove(key)
        data.remove(key)
    }
}