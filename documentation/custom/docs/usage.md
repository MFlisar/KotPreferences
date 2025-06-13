---
icon: material/keyboard
---

#### 1. Define a `SettingsModel`

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

#### 2a) Use the `SettingsModel` in plain kotlin (`flows` + `suspending functions`)

```kotlin
// 1) get a flow
val flow = Preferences.someString.flow

// 2) read/update values by suspend functions
scope.launch(Dispatchers.IO) {
    val value = Preferences.someInt.read()
    Preferences.someInt.update(value + 1)
}
```

#### 2b) Use the `SettingsModel` in views (e.g. with `Lifecycle Scope`)

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

#### 2c) Use the `SettingsModel` in `compose`

```kotlin
val name = Preferences.someString.collectAsState()
val name = Preferences.someString.collectAsStateWithLifecycle()

// simply use the state inside your composables, the state will change whenever the setting behind it will change

val name = Preferences.someString.asMutableState()

// simple use and even update the state now

```