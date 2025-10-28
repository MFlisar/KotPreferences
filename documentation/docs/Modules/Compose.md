---
icon: material/puzzle
---

This module is placed inside the `extension-compose` artifact and offers some helpful compose extensions for the `Storage` class.

All extensions functions are on `StorageSetting<T>` and following are available:

Available extensions are listed below:

??? code "`StorageSetting<T>.collectAsState(...)` functions"

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsStateExtensions.kt:collectAsState1"
    ```

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsStateExtensions.kt:collectAsState2"
    ```

??? code "`StorageSetting<T>.collectAsStateNotNull(...)` functions"

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsStateExtensions.kt:collectAsStateNotNull1"
    ```
    
    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsStateExtensions.kt:collectAsStateNotNull2"
    ```

??? code "`StorageSetting<T>.collectAsStateWithLifecycle(...)` functions"

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsStateWithLifecycleExtensions.kt:collectAsStateWithLifecycle1"
    ```

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsStateWithLifecycleExtensions.kt:collectAsStateWithLifecycle2"
    ```

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsStateWithLifecycleExtensions.kt:collectAsStateWithLifecycle3"
    ```

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsStateWithLifecycleExtensions.kt:collectAsStateWithLifecycle4"
    ```

??? code "`StorageSetting<T>.collectAsStateWithLifecycleNotNull(...)` functions"

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsStateWithLifecycleExtensions.kt:collectAsStateWithLifecycleNotNull1"
    ```
    
    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsStateWithLifecycleExtensions.kt:collectAsStateWithLifecycleNotNull2"
    ```

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsStateWithLifecycleExtensions.kt:collectAsStateWithLifecycleNotNull3"
    ```

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/CollectAsStateWithLifecycleExtensions.kt:collectAsStateWithLifecycleNotNull4"
    ```

??? code "`StorageSetting<T>.asMutableState(...)` functions"

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateExtensions.kt:asMutableState1"
    ```
    
    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateExtensions.kt:asMutableState2"
    ```

??? code "`StorageSetting<T>.asMutableStateNotNull(...)` functions"

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateExtensions.kt:asMutableStateNotNull1"
    ```
    
    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateExtensions.kt:asMutableStateNotNull2"
    ```

??? code "`StorageSetting<T>.asMutableStateWithLifecycle(...)` functions"

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateWithLifecycleExtensions.kt:asMutableStateWithLifecycle1"
    ```
    
    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateWithLifecycleExtensions.kt:asMutableStateWithLifecycle2"
    ```

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateWithLifecycleExtensions.kt:asMutableStateWithLifecycle3"
    ```
    
    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateWithLifecycleExtensions.kt:asMutableStateWithLifecycle4"
    ```

??? code "`StorageSetting<T>.asMutableStateWithLifecycleNotNull(...)` functions"

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateWithLifecycleExtensions.kt:asMutableStateWithLifecycleNotNull1"
    ```
    
    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateWithLifecycleExtensions.kt:asMutableStateWithLifecycleNotNull2"
    ```

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateWithLifecycleExtensions.kt:asMutableStateWithLifecycleNotNull3"
    ```
    
    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/MutableStateWithLifecycleExtensions.kt:asMutableStateWithLifecycleNotNull4"
    ```

??? code "`StorageSetting<T>.asMutableStateWithLifecycleNotNull(...)` functions"
    
    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/StateFlowExtensions.kt:asStateFlow1"
    ```
    
    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/StateFlowExtensions.kt:asStateFlow1"
    ```

??? code "`StorageSetting<T>.asStateFlowNotNull(...)` functions"

    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/StateFlowExtensions.kt:asStateFlowNotNull1"
    ```
    
    ```kotlin
    --8<-- "../../library/modules/compose/src/commonMain/kotlin/com/michaelflisar/kotpreferences/compose/StateFlowExtensions.kt:asStateFlowNotNull2"
    ```