package com.michaelflisar.kotpreferences.demo.classes

import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.toMutableStateList
import kotlin.random.Random

object DemoUtil {

    private val CHARS: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun randomString(length: Int) = (1..length)
        .map { Random.nextInt(0, CHARS.size).let { CHARS[it] } }
        .joinToString("")

    val LIST_SAVER = listSaver<MutableList<String>, String>(
        save = {
            it.toList()
        },
        restore = { it.toMutableStateList() }
    )
}