---
icon: material/puzzle
---

The `Storage` is an abstraction to support any storage implementation. The `storage-keyvalue` module provides an implementation based on a plain text file that will look like following:

```text
key1=value1
key2=value2
```

This module is placed inside the `storage-keyvalue` artifact and can simply be used like following:

=== "Android/iOS"

    ```kotlin
    object SettingsModel : SettingsModel(
        KeyValueStorage.create(
            fileName: String = "settings.txt",
            delimiter: String = "=",
            encryption: StorageEncryption? = null,
            cache: Boolean = true
        )
    ) {
        // ...
    }
    ```

=== "JVM"

    ```kotlin
    object SettingsModel : SettingsModel(
        KeyValueStorage.create(
            folder = File(System.getProperty("user.dir")),
            fileName = "data.txt",
            delimiter: String = "=",
            encryption: StorageEncryption? = null,
            cache: Boolean = true
        )
    ) {
        // ...
    }
    ```