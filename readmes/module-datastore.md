## Storage DataStore Module

The `Storage` is an abstraction to support any storage implementation. The `datastore` module provides an implementation based on the [Android JetPack DataStore](https://developer.android.com/topic/libraries/architecture/datastore).

This module is placed inside the `datastore` artifact and can simple be used like following:

#### Android/iOS Implementation

```kotlin
object SettingsModel : SettingsModel(
    DataStoreStorage.create(
        name: String = "settings",
        cache: Boolean = true,
        encryption: StorageEncryption? = null
    )
) {
    // ...
}
```

#### JVM Implementation

```kotlin
object SettingsModel : SettingsModel(
    DataStoreStorage.create(
        folder: File,
        name: String = "settings",
        cache: Boolean = true,
        encryption: StorageEncryption? = null
    )
) {
    // ...
}
```
