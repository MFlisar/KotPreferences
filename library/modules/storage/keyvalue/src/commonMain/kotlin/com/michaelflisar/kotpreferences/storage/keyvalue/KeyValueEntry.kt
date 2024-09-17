package com.michaelflisar.kotpreferences.storage.keyvalue

import com.michaelflisar.kotpreferences.core.classes.StorageDataType
import com.michaelflisar.kotpreferences.core.interfaces.StorageEncryption

class KeyValueEntry<T : Any?>(
    val entry: T,
    val type: StorageDataType
) {
    companion object {

        fun <T : Any?> create(
            line: String,
            type: StorageDataType,
            encryption: StorageEncryption?
        ): KeyValueEntry<T> {
            return if (encryption != null) {
                KeyValueEntry<T & Any>(encryption.decrypt(line, type.base), type)
            } else {
                when (type.base) {
                    is StorageDataType.String -> KeyValueEntry(line, type)
                    is StorageDataType.Boolean -> KeyValueEntry(line.toBoolean(), type)
                    is StorageDataType.Int -> KeyValueEntry(line.toInt(), type)
                    is StorageDataType.Long -> KeyValueEntry(line.toLong(), type)
                    is StorageDataType.Float -> KeyValueEntry(line.toFloat(), type)
                    is StorageDataType.Double -> KeyValueEntry(line.toDouble(), type)
                    is StorageDataType.StringSet -> KeyValueEntry(line.drop(1).dropLast(1).takeIf { it.isNotEmpty() }?.split(", ")?.toSet() ?: emptySet<String>(), type) // TODO: save machen
                }
            } as KeyValueEntry<T>
        }
    }

    fun entryAsString(
        encryption: StorageEncryption?
    ): String? {
        if (entry == null)
            return null

        return if (encryption != null) {
            encryption.encrypt(entry, type.base)
        } else {
            val s = when (type.base) {
                is StorageDataType.String -> entry as String
                is StorageDataType.Boolean -> entry as Boolean
                is StorageDataType.Int -> entry as Int
                is StorageDataType.Long -> entry as Long
                is StorageDataType.Float -> entry as Float
                is StorageDataType.Double -> entry as Double
                is StorageDataType.StringSet -> entry // TODO: make it save...???
            }
            return s.toString()
        }
    }
}
