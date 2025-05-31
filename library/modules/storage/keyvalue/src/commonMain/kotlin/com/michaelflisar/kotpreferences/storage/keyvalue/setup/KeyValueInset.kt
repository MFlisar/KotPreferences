package com.michaelflisar.kotpreferences.storage.keyvalue.setup

/**
 * Interface for calculating the inset for multiline values.
 */
interface KeyValueInset {
    /**
     * Provides the inset for continued lines of the value based on the key.
     * @param key The key for which an inset is calculated.
     * @return The indentation string for subsequent lines.
     */
    fun getInsetForKey(key: String): String
}

/**
 * A fixed, configurable inset (e.g., 4 spaces).
 */
class KeyValueFixedInset(private val value: String = "    ") : KeyValueInset {
    override fun getInsetForKey(key: String): String = value
}

/**
 * Dynamic block inset:
 * Creates an inset with (key.length + 1) characters, e.g., spaces.
 */
class KeyValueBlockInset(private val fillChar: Char = ' ') : KeyValueInset {
    override fun getInsetForKey(key: String): String = fillChar.toString().repeat(key.length + 1)
}

/**
 * No inset – continuation lines are not allowed or have no indentation.
 *
 * this inset is not safe to use with multiline values!
 */
object KeyValueNoInset : KeyValueInset {
    override fun getInsetForKey(key: String): String = ""
}