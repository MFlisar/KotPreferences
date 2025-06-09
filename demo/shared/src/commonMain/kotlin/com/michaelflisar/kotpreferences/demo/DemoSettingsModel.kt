package com.michaelflisar.kotpreferences.demo

import com.michaelflisar.kotpreferences.core.SettingsModel
import com.michaelflisar.kotpreferences.core.interfaces.Storage
import com.michaelflisar.kotpreferences.demo.classes.TestClass
import com.michaelflisar.kotpreferences.demo.classes.TestEnum

class DemoSettingsModel(
    storage: Storage
) : SettingsModel(storage) {

    // simple types
    val testString by stringPref("initial value")
    val testInt by intPref()
    val testBool by boolPref()
    val testFloat by floatPref()
    val testLong by longPref()

    // enum
    val testEnum by enumPref(TestEnum.Blue, TestEnum.entries)

    // custom class
    val testClass by anyStringPref(TestClass.CONVERTER, TestClass())

    // NULLABLE vs NON NULLABLE
    val nonNullableString by stringPref()
    val nullableString by nullableStringPref()
    val nonNullableInt by intPref()
    val nullableInt by nullableIntPref()
    val nonNullableFloat by floatPref()
    val nullableFloat by nullableFloatPref()
    val nonNullableDouble by doublePref()
    val nullableDouble by nullableDoublePref()
    val nonNullableLong by longPref()
    val nullableLong by nullableLongPref()
    val nonNullableBool by boolPref()
    val nullableBool by nullableBoolPref()

    // others
    val counter by intPref(0)
    val color by longPref(0xFF000000)
    val nullableColor by nullableLongPref(0xFF000000)

    // simple non nullable
    val int1 by intPref(1234)
    val bool1 by boolPref(false)
    val string1 by stringPref("Input 1")
    val long1 by longPref(100L)
    val float1 by floatPref(200.5f)
    val double1 by doublePref(300.5)

    // simple nullable
    val int2 by nullableIntPref(1234)
    val bool2 by nullableBoolPref(false)
    val string2 by nullableStringPref("Input 1")
    val long2 by nullableLongPref(100L)
    val float2 by nullableFloatPref(200.5f)
    val double2 by nullableDoublePref(300.5)

    // sets
    val stringSet1 by stringSetPref(setOf("a", "b", "c"))
    val intSet1 by intSetPref(setOf(1, 2, 3))
    val longSet1 by longSetPref(setOf(1L, 2L, 3L))
    val floatSet1 by floatSetPref(setOf(1f, 2f, 3f))
    val doubleSet1 by doubleSetPref(setOf(1.0, 2.0, 3.0))

    val string by nullableStringPref("")
    val intSet by intSetPref()
}