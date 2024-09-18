package com.michaelflisar.kotpreferences.demo.settings

import com.michaelflisar.composedemobaseactivity.classes.DemoTheme
import com.michaelflisar.composethemer.ComposeTheme
import com.michaelflisar.composethemer.themes.themes.ThemeGreens
import com.michaelflisar.kotpreferences.core.SettingsModel
import com.michaelflisar.kotpreferences.core.enumPref
import com.michaelflisar.kotpreferences.core.initialisation.SettingSetup
import com.michaelflisar.kotpreferences.storage.datastore.DataStoreStorage
import com.michaelflisar.kotpreferences.storage.datastore.create

object DemoSettingsModel : SettingsModel(
    DataStoreStorage.create(
        name = "demo_settings",
        cache = SettingSetup.ENABLE_CACHING, // false by default, only relevant for blocking reads
        //encryption = DemoStorageEncryption
    )
) {

    // app settings
    val baseTheme by enumPref(ComposeTheme.BaseTheme.System)
    val theme by stringPref(ThemeGreens.KEY)
    val dynamicTheme by boolPref(false)

    // simple types
    val testString by stringPref("initial value")
    val testInt by intPref()
    val testBool by boolPref()
    val testFloat by floatPref()
    val testLong by longPref()

    // enum
    val testEnum by enumPref(TestEnum.Blue)

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
}