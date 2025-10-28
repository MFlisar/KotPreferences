---
icon: material/puzzle
---

This module is placed inside the `extension-compose` artifact and offers some helpful compose extensions for the `Storage` class.

All extensions functions are on `StorageSetting<T>` and following are available:

Available extensions are following:

* `StorageSetting<T>.collectAsStateNotNull(...)`
* `StorageSetting<T>.collectAsStateWithLifecycle(...)`
* `StorageSetting<T>.asMutableState(...)`
* `StorageSetting<T>.asMutableStateNotNull(...)`
* `StorageSetting<T>.asMutableStateWithLifecycle(...)`
* `StorageSetting<T>.asMutableStateNotNullWithLifecycle(...)`
* `StorageSetting<T>.asStateFlow(...)`
* `StorageSetting<T>.asStateFlowNotNull(...)`

For more details check the signatures below:

!!! code `collectAsState*` functions

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsStateExtensions.kt:collectAsState1"
    ```

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsStateExtensions.kt:collectAsState2"
    ```

!!! code `collectAsStateNotNull*` functions

```kotlin
--8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsStateExtensions.kt:collectAsStateNotNull1"
```

```kotlin
--8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsStateExtensions.kt:collectAsStateNotNull2"
```

#### `asMutableState*` functions

```kotlin
--8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateExtensions.kt:asMutableState1"
```

```kotlin
--8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateExtensions.kt:asMutableState2"
```

```kotlin
--8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateExtensions.kt:asMutableStateNotNull1"
```

```kotlin
--8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateExtensions.kt:asMutableStateNotNull2"
```

#### `asStateFlow*` functions

```kotlin
--8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/StateFlowExtensions.kt:asStateFlow1"
```

```kotlin
--8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/StateFlowExtensions.kt:asStateFlow1"
```

```kotlin
--8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/StateFlowExtensions.kt:asStateFlowNotNull1"
```

```kotlin
--8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/StateFlowExtensions.kt:asStateFlowNotNull2"
```