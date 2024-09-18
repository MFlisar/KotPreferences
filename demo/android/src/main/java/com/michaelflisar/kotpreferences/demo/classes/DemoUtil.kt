package com.michaelflisar.kotpreferences.demo.classes

import kotlin.random.Random

object DemoUtil {

    private val CHARS: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun randomString(length: Int) = (1..length)
        .map { Random.nextInt(0, CHARS.size).let { CHARS[it] } }
        .joinToString("")
}