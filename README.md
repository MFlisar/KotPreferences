[![Maven](https://img.shields.io/maven-central/v/io.github.mflisar.kotpreferences/core?style=for-the-badge&color=blue)](https://central.sonatype.com/namespace/io.github.mflisar.kotpreferences)
[![API](https://img.shields.io/badge/api-21%2B-brightgreen.svg?style=for-the-badge)](https://android-arsenal.com/api?level=21)
[![Kotlin](https://img.shields.io/github/languages/top/mflisar/kotpreferences.svg?style=for-the-badge&color=blueviolet)](https://kotlinlang.org/)
[![KMP](https://img.shields.io/badge/Kotlin_Multiplatform-blue?style=for-the-badge&label=Kotlin)](https://kotlinlang.org/docs/multiplatform.html)
[![License](https://img.shields.io/github/license/MFlisar/KotPreferences?style=for-the-badge)](LICENSE)

<h1 align="center">KotPreferences</h1>

A <b>kotlin delegates</b> based preference library.

## :heavy_check_mark: Features

With this library you can declare preferences via **kotlin delegates** and observe and update them via **kotlin flows**. This works with any storage implementation, an implementation for JetPack DataStore is provided already.

Additionally there's also an extension to easily integrate this library into your compose app.

**All features are splitted into separate modules, just include the modules you want to use!**

## :camera: Screenshots

--

## :link: Dependencies

This library does not have any custom dependencies!

## :elephant: Gradle

This library is distributed via [maven central](https://central.sonatype.com/).

*build.gradle.kts*

```kts
val kotpreferences = "<LATEST-VERSION>"

// core
implementation("io.github.mflisar.kotpreferences:core:$kotpreferences")

// storage modules
implementation("io.github.mflisar.kotpreferences:storage-datastore:$kotpreferences")
implementation("io.github.mflisar.kotpreferences:storage-keyvalue:$kotpreferences")

// encryption modules
implementation("io.github.mflisar.kotpreferences:encryption-aes:$kotpreferences")

// extension modules
implementation("io.github.mflisar.kotpreferences:extension-compose:$kotpreferences")
```

## :zap: Modules

| Module              | Info     | Description                                                                                                                                        |
|---------------------|----------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| `core`              |          | the core module - must always be included                                                                                                          |
| `storage-datastore` |          | the default datastore based storage module - you probably want that as well if you do not want to provide a custom storage implementation yourself |
| `storage-keyvalue`  |          | an alternative storage module that simply saves your data in a plain key-value based text file                                                     |
| `encryption-aes`    | optional | provides an encryption implementation                                                                                                              |
| `extension-compose` | optional | provides convenient extensions that can be used inside compose                                                                                     |

## </> Basic Usage

<details open>
<summary><b>1.</b> Define a <code>SettingsModel</code></summary>

```kotlin
// Depending on the platform:
// jvm: DataStoreStorage.create(folder = File(System.getProperty("user.dir")), name = "settings")
// android/iOS: DataStoreStorage.create(name = "preferences")
object Preferences : SettingsModel(DataStoreStorage.create(name = "preferences")) {

    // main data types
    val someString by stringPref("value")
    val someBool by boolPref(false)
    val someInt by intPref(123)
    val someLong by intPref(123L)
    val someFloat by intPref(123f)
    val someDouble by intPref(123.0)

    // enum
    val someEnum by enumPref(Enum.Value1)

    // custom
    val someCustomClass1 by anyStringPref(TestClass.CONVERTER, TestClass()) // converts TestClass to a string and saves this string
    val someCustomClass2 by anyIntPref(TestClass.CONVERTER, TestClass())    // converts TestClass to an int and saves this int
    val someCustomClass3 by anyLongPref(TestClass.CONVERTER, TestClass())   // converts TestClass to a long and saves this long

    // sets
    val someStringSet by stringSetPref(setOf("a"))
    val someIntSet by intSetPref(setOf(1))
    val someLongSet by longSetPref(setOf(1L))
    val someFloatSet by floatSetPref(setOf(1f))
    val someDoubleSet by doubleSetPref(setOf(1.0))

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

    // custom
    val someCustomClass4 by nullableAnyStringPref(TestClass.CONVERTER, TestClass())
    val someCustomClass5 by nullableAnyIntPref(TestClass.CONVERTER, TestClass())
    val someCustomClass6 by nullableAnyLongPref(TestClass.CONVERTER, TestClass())
}
```

</details>

<details>
<summary><b>2a.</b> Use the <code>SettingsModel</code> in plain kotlin (<code>Flows</code> + <code>suspending functions</code>)</summary>

```kotlin
// 1) get a flow
val flow = Preferences.someString.flow

// 2) read/update values by suspend functions
scope.launch(Dispatchers.IO) {
    val value = Preferences.someInt.read()
    Preferences.someInt.update(value + 1)
}
```

</details>

<details>
<summary><b>2b.</b> Use the <code>SettingsModel</code> in views (e.g. <code>Lifecycle Scope</code>)</summary>

```kotlin
// 1) simply observe a setting
Preferences.someString.observe(lifecycleScope) {
    L.d { "someString = $it"}
}

// 2) direct read (not recommended if not necessary but may be useful in many cases)
// => simply returns read() in a blocking way)
val name = Preferences.someString.value

// 3) observe a setting once
Preferences.someString.observeOnce(lifecycleScope) {
    L.d { "someString = $it"}
}

// 4) observe ALL settings
Preferences.changes.onEach {
    L.d { "[ALL SETTINGS OBSERVER] Setting '${it.setting.key}' changed its value to ${it.value}" }
}.launchIn(lifecycleScope)

// 5) observe SOME settings
Preferences.changes
    .filter {
        it.setting == Preferences.someString ||
        it.setting == Preferences.someBool
    }.onEach {
        L.d { "[SOME SETTINGS OBSERVER] Setting '${it.setting.key}' changed its value to ${it.value}" }
    }.launchIn(lifecycleScope)

// 6) read multiple settings in a suspending way
lifecycleScope.launch(Dispatchers.IO) {
    val someString = Preferences.someString.read()
    val someBool = Preferences.someBool.read()
}
```

</details>

<details>
<summary><b>2c.</b> Use the <code>SettingsModel</code> inside compose</summary>

```kotlin
val name = Preferences.someString.collectAsState()
val name = Preferences.someString.collectAsStateWithLifecycle()

// simply use the state inside your composables, the state will change whenever the setting behind it will change

val name = Preferences.someString.asMutableState()

// simple use and even update the state now

```

</details>

## :computer: Supported Platforms

This is a **KMP (kotlin multiplatform)** library and the provided modules do support following platforms:

| Modules           | Android | iOS | jvm | Information |
|:------------------|---------|-----|-----|-------------|
| core              | √       | √   | √   |             |
| storage-datastore | √       | √   | √   |             |
| storage-keyvalue  | √       | √   | √   |             |
| encryption-aes    | √       |     |     | (1)         |
| extension-compose | √       | √   | √   |             |

* (1) Currently I only provide an encryption module for android. Pull requests with implementations for other platforms are welcome.

## :tada: Demo

A full [demo](demo) is included inside the demo module, it shows nearly every usage with working examples.

## :information_source: More Informations

* [Encryption Module](readmes/module-encryption.md)
* [Storage - DataStore Module](readmes/module-datastore.md)
* [Storage - Key-Value Module](readmes/module-key-value.md)
