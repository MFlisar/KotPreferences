package com.michaelflisar.kotpreferences.storage.keyvalue

import com.michaelflisar.kotpreferences.core.classes.StorageDataType
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption

class KeyValueData {

    private var lines: MutableMap<String, String> = HashMap()
    private var data: MutableMap<String, KeyValueEntry<*>> = HashMap()

    fun clear() {
        lines.clear()
        data.clear()
    }

    fun hasKey(key: String) = lines.containsKey(key) || data.containsKey(key)

    fun getAllLines(encryption: StorageEncryption?): Map<String, String> {
        return lines + data.mapNotNull { d ->
            val line = d.value.entryAsString(encryption)
            line?.let { Pair(d.key, it) }
        }
    }

    fun <T> getEntry(
        key: String,
        type: StorageDataType,
        encryption: StorageEncryption?
    ): KeyValueEntry<T>? {
        val entry = data[key] ?: lines[key]?.let { KeyValueEntry.create<T>(it, type, encryption) }
        return entry as KeyValueEntry<T>?
    }

    fun addLines(lines: Map<String, String>) {
        this.lines += lines
    }

    fun <T> addEntry(key: String, entry: KeyValueEntry<T>) {
        lines.remove(key)
        data[key] = entry
    }
}