package com.michaelflisar.kotpreferences.storage.keyvalue

import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption

import kotlinx.browser.window
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun KeyValueStorage.Companion.create(
    key: String = "settings",
    encryption: StorageEncryption? = null,
    cache: Boolean = true,
) = KeyValueStorage(
    read = { read(key) },
    exists = { window.localStorage.getItem(key) != null },
    write = { write(key, it) },
    encryption = encryption,
    cache = cache,
)

internal fun write(key: String, map: List<KeyValue>) {
    val data = Data(map.map { it.key to it.value }.toMap())
    val value = Json.encodeToString(data)
    window.localStorage.setItem(key, value)
}

internal fun read(key: String): List<KeyValue> {
    val value = window.localStorage.getItem(key)
    return if (value == null) {
        emptyList()
    } else {
        Json.decodeFromString<Data>(value).map.map { KeyValue(it.key, it.value) }
    }
}

@Serializable
internal class Data(
    val map: Map<String, String>,
)