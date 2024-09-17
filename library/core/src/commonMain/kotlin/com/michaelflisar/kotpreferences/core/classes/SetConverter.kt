package com.michaelflisar.kotpreferences.core.classes

object SetConverter {

    fun convertIntSetToStringSet(data: Set<Int>) = data.map { it.toString() }.toSet()
    fun convertStringToIntSet(data: Set<String>) = data.map { it.toInt() }.toSet()

    fun convertLongSetToStringSet(data: Set<Long>) = data.map { it.toString() }.toSet()
    fun convertStringToLongSet(data: Set<String>) = data.map { it.toLong() }.toSet()

    fun convertFloatSetToStringSet(data: Set<Float>) = data.map { it.toString() }.toSet()
    fun convertStringToFloatSet(data: Set<String>) = data.map { it.toFloat() }.toSet()

    fun convertDoubleSetToStringSet(data: Set<Double>) = data.map { it.toString() }.toSet()
    fun convertStringToDoubleSet(data: Set<String>) = data.map { it.toDouble() }.toSet()
}