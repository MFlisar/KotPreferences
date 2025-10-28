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

??? code "`collectAsState*` functions"

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsStateExtensions.kt:collectAsState1"
    ```

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsStateExtensions.kt:collectAsState2"
    ```

??? code "`collectAsStateNotNull*` functions"

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsStateExtensions.kt:collectAsStateNotNull1"
    ```
    
    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsStateExtensions.kt:collectAsStateNotNull2"
    ```

??? code "`collectAsStateWithLifecycle*` functions"

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsWithLifecycleStateExtensions.kt:collectAsStateWithLifecycle1"
    ```

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsWithLifecycleStateExtensions.kt:collectAsStateWithLifecycle2"
    ```

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsWithLifecycleStateExtensions.kt:collectAsStateWithLifecycle3"
    ```

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsWithLifecycleStateExtensions.kt:collectAsStateWithLifecycle4"
    ```

??? code "`collectAsStateWithLifecycleNotNull*` functions"

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsWithLifecycleStateExtensions.kt:collectAsStateWithLifecycleNotNull1"
    ```
    
    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsWithLifecycleStateExtensions.kt:collectAsStateWithLifecycleNotNull2"
    ```

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsWithLifecycleStateExtensions.kt:collectAsStateWithLifecycleNotNull3"
    ```

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsWithLifecycleStateExtensions.kt:collectAsStateWithLifecycleNotNull4"
    ```

??? code "`asMutableState*` functions"

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateExtensions.kt:asMutableState1"
    ```
    
    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateExtensions.kt:asMutableState2"
    ```

??? code "`asMutableStateNotNull*` functions"

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateExtensions.kt:asMutableStateNotNull1"
    ```
    
    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateExtensions.kt:asMutableStateNotNull2"
    ```

??? code "`asMutableStateWithLifecycle*` functions"

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateWithLifecycleExtensions.kt:asMutableStateWithLifecycle1"
    ```
    
    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateWithLifecycleExtensions.kt:asMutableStateWithLifecycle2"
    ```

??? code "`asMutableStateWithLifecycleNotNull*` functions"

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateWithLifecycleExtensions.kt:asMutableStateWithLifecycleNotNull1"
    ```
    
    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateWithLifecycleExtensions.kt:asMutableStateWithLifecycleNotNull2"
    ```

??? code "`asMutableStateWithLifecycleNotNull*` functions"
    
    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/StateFlowExtensions.kt:asStateFlow1"
    ```
    
    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/StateFlowExtensions.kt:asStateFlow1"
    ```

??? code "`asStateFlowNotNull*` functions"

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/StateFlowExtensions.kt:asStateFlowNotNull1"
    ```
    
    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/StateFlowExtensions.kt:asStateFlowNotNull2"
    ```