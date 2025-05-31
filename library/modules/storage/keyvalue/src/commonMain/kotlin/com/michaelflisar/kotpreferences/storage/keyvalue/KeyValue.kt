package com.michaelflisar.kotpreferences.storage.keyvalue

/**
 * Data class representing a key-value pair.
 * Values may span multiple lines.
 */
data class KeyValue(
    val key: String,
    val value: String
)