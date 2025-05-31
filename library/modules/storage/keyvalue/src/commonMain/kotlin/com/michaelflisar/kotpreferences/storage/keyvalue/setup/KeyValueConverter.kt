package com.michaelflisar.kotpreferences.storage.keyvalue.setup

import com.michaelflisar.kotpreferences.storage.keyvalue.KeyValue

/**
 * A class for reading and writing human-readable key-value data.
 * Supports multi-line values using indentation.
 *
 * @param separator the string used to separate key and value (default: '=')
 * @param inset the indentation strategy for multi-line values
 */
class KeyValueConverter(
    private val separator: String = KeyValueStorageDefaults.SEPARATOR,
    private val inset: KeyValueInset = KeyValueStorageDefaults.INSET,
) {
    /**
     * Parses key-value pairs from a text input.
     * Multi-line values must be consistently indented.
     */
    fun readFromString(input: String): List<KeyValue> {
        val entries = mutableListOf<KeyValue>()
        if (input.isEmpty())
            return entries
        val lines = input.lines()
        var currentKey: String? = null
        val currentValue = StringBuilder()
        var currentInset: String? = null

        for (line in lines) {
            if (line.isBlank() && currentKey == null) continue

            val isNewEntry = line.contains(separator) && (currentInset == null || !line.startsWith(currentInset))

            if (isNewEntry) {
                currentKey?.let {
                    entries.add(KeyValue(it, currentValue.toString()))
                    currentValue.clear()
                }

                val splitIndex = line.indexOf(separator)
                require(splitIndex != -1) { "Invalid line (missing separator '$separator'): $line" }

                val key = line.substring(0, splitIndex)
                require(!key.contains(' ')) { "Keys must not contain whitespace: '$key'" }

                currentKey = key
                currentInset = inset.getInsetForKey(key)
                currentValue.append(line.substring(splitIndex + separator.length))
            } else {
                currentValue.append("\n")
                currentValue.append(line.removePrefix(currentInset ?: ""))
            }
        }

        // Add last entry
        currentKey?.let {
            entries.add(KeyValue(it, currentValue.toString()))
        }

        return entries
    }

    /**
     * Serializes key-value entries to a formatted string.
     * Multi-line values are indented according to the inset strategy.
     */
    fun writeToString(entries: List<KeyValue>): String {
        val data = buildString {
            entries.forEachIndexed { index, (key, value) ->
                val lines = value.split("\n")
                val currentInset = inset.getInsetForKey(key)
                append("$key$separator${lines.first()}")
                for (line in lines.drop(1)) {
                    append("\n$currentInset$line")
                }
                if (index < entries.size - 1) {
                    append("\n")
                }
            }
        }
        return data
    }
}
