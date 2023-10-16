### About

[![Release](https://jitpack.io/v/MFlisar/KotPreferences.svg)](https://jitpack.io/#MFlisar/KotPreferences)
![License](https://img.shields.io/github/license/MFlisar/KotPreferences)

### Gradle (via [JitPack.io](https://jitpack.io/))

1. add jitpack to your project's `build.gradle`:

```
repositories {
    maven { url "https://jitpack.io" }
}
```

2. add the compile statement to your module's `build.gradle`:

```
dependencies {

  val kotPreferences = "<LATEST-VERSION>"
  
  // core module
  implementation("com.github.MFlisar.KotPreferences:core:$kotPreferences")
  
  // data store module
  implementation("com.github.MFlisar.KotPreferences:datastore:$kotPreferences")
  implementation("com.github.MFlisar.KotPreferences:encryption-aes:$kotPreferences")
  
  // compose state extensions
  implementation("com.github.MFlisar.KotPreferences:compose:$kotPreferences")
}
```

The latest release can be found [here](https://github.com/MFlisar/KotPreferences/releases/latest)

### Demo

A full demo is included inside the [demo module](https://github.com/MFlisar/KotPreferences/tree/main/demo), it shows nearly every usage with working examples.

# Example

With this library you can declare preferences via kotlin `delegates` and observe and update them via kotlin `Flows`. This works with any storage implementation, an implementation for JetPack DataStore is provided already.

##### 1/5 Define preferences:

```kotlin
object UserSettingsModel : SettingsModel(DataStoreStorage(name = "user")) {

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
    val someCustomClass by anyPref(TestClass.CONVERTER, TestClass())
    
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

}
```

##### 2/5 Observe/Read preferences:

```kotlin
// 1) simply observe a setting
UserSettingsModel.name.observe(lifecycleScope) {
    L.d { "name = $it"}
}

// 2) direct read (not recommended if not necessary but may be useful in many cases => simply returns flow.first() in a blocking way)
val name = UserSettingsModel.name.value

// 3) observe a setting once
UserSettingsModel.name.observeOnce(lifecycleScope) {
    L.d { "name = $it"}
}

// 4) observe ALL settings
UserSettingsModel.changes.onEach {
    L.d { "[ALL SETTINGS OBSERVER] Setting '${it.setting.key}' changed its value to ${it.value}" }
}.launchIn(lifecycleScope)

// 5) observe SOME settings
UserSettingsModel.changes
    .filter {
        it.setting == UserSettingsModel.name ||
        it.setting == UserSettingsModel.age
    }.onEach {
        // we know that either the name or the age changes
        L.d { "[SOME SETTINGS OBSERVER] Setting '${it.setting.key}' changed its value to ${it.value}" }
    }.launchIn(lifecycleScope)
    
// 6) read multiple settings in a suspending way
lifecycleScope.launch(Dispatchers.IO) {
    val name = UserSettingsModel.childName1.flow.first()
    val alive = DemoSettingsModel.alive.flow.first()
    val hairColor = DemoSettingsModel.hairColor.flow.first()
    withContext(Dispatchers.Main) {
        textView.text = "Informations: $name, $alive, $hairColor"
    }
}
```

##### 3/5 Lifedata:

```kotlin
val lifedata = UserSettingsModel.name.flow.asLiveData()
```

##### 4/5 Update preferences:

```kotlin
lifecycleScope.launch(Dispatchers.IO)  {
    UserSettingsModel.name.update("Some new name")
    UserSettingsModel.age.update(30)
}
```

##### 5/5 Compose support:

Add the `compose` module to get following extensions for compose.

```kotlin
val name = UserSettingsModel.name.collectAsState()
val name = UserSettingsModel.name.collectAsStateWithLifecycle()

// simply use the state inside your composables, the state will change whenever the setting behind it will change

```

# Special Documentation

The modules are documented in their own readmes:

* [encryption module](doc/encryption.md)
* [storage datastore module](doc/storage-datastore.md)