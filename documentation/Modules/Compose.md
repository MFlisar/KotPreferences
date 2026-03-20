This module is placed inside the `extension-compose` artifact and offers some helpful compose extensions for the `Storage` class.

All extensions functions are on `StorageSetting<T>` and following are available:

Available extensions are listed below:

<details>

<summary><b><code>StorageSetting<T>.collectAsState(...)</code> functions</b></summary>

<!-- snippet: CollectAsStateExtensions::collectAsState1 -->
```kt
@Composable
fun <T> StorageSetting<T>.collectAsState(
    initialValue: T? = getCachedValue()
): State<T?>
```
<!-- endSnippet -->

<!-- snippet: CollectAsStateExtensions::collectAsState2 -->
```kt
@Composable
fun <T, X> StorageSetting<T>.collectAsState(
    initialValue: T? = getCachedValue(),
    mapper: (T) -> X,
): State<X?>
```
<!-- endSnippet -->

</details>

<details>

<summary><b><code>StorageSetting<T>.collectAsStateNotNull(...)</code> functions</b></summary>

<!-- snippet: CollectAsStateExtensions::collectAsStateNotNull1 -->
```kt
@Composable
fun <T> StorageSetting<T>.collectAsStateNotNull(
    initialValue: T = getValueNotNull()
): State<T>
```
<!-- endSnippet -->

<!-- snippet: CollectAsStateExtensions::collectAsStateNotNull2 -->
```kt
@Composable
fun <T, X> StorageSetting<T>.collectAsStateNotNull(
    initialValue: T = getValueNotNull(),
    mapper: (T) -> X,
): State<X>
```
<!-- endSnippet -->

</details>

<details>

<summary><b><code>StorageSetting<T>.collectAsStateNotNull(...)</code> functions</b></summary>

<!-- snippet: CollectAsStateWithLifecycleExtensions::collectAsStateWithLifecycle1 -->
```kt
@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycle(
    lifecycle: Lifecycle,
    initialValue: T? = tryGetValueNotNull(),
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
): State<T?>
```
<!-- endSnippet -->

<!-- snippet: CollectAsStateWithLifecycleExtensions::collectAsStateWithLifecycle2 -->
```kt
@Composable
fun <T, X> StorageSetting<T>.collectAsStateWithLifecycle(
    lifecycle: Lifecycle,
    initialValue: T? = tryGetValueNotNull(),
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
): State<X?>
```
<!-- endSnippet -->

<!-- snippet: CollectAsStateWithLifecycleExtensions::collectAsStateWithLifecycle3 -->
```kt
@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycle(
    initialValue: T? = tryGetValueNotNull(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
): State<T?>
```
<!-- endSnippet -->

<!-- snippet: CollectAsStateWithLifecycleExtensions::collectAsStateWithLifecycle4 -->
```kt
@Composable
fun <T, X> StorageSetting<T>.collectAsStateWithLifecycle(
    initialValue: T? = tryGetValueNotNull(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
): State<X?>
```
<!-- endSnippet -->

</details>

<details>

<summary><b><code>StorageSetting<T>.collectAsStateWithLifecycleNotNull(...)</code> functions</b></summary>

<!-- snippet: CollectAsStateWithLifecycleExtensions::collectAsStateWithLifecycleNotNull1 -->
```kt
@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycleNotNull(
    lifecycle: Lifecycle,
    initialValue: T = getValueNotNull(),
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
): State<T>
```
<!-- endSnippet -->

<!-- snippet: CollectAsStateWithLifecycleExtensions::collectAsStateWithLifecycleNotNull2 -->
```kt
@Composable
fun <T, X> StorageSetting<T>.collectAsStateWithLifecycleNotNull(
    lifecycle: Lifecycle,
    initialValue: T = getValueNotNull(),
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
): State<X>
```
<!-- endSnippet -->

<!-- snippet: CollectAsStateWithLifecycleExtensions::collectAsStateWithLifecycleNotNull3 -->
```kt
@Composable
fun <T> StorageSetting<T>.collectAsStateWithLifecycleNotNull(
    initialValue: T = getValueNotNull(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
): State<T>
```
<!-- endSnippet -->

<!-- snippet: CollectAsStateWithLifecycleExtensions::collectAsStateWithLifecycleNotNull4 -->
```kt
@Composable
fun <T, X> StorageSetting<T>.collectAsStateWithLifecycleNotNull(
    initialValue: T = getValueNotNull(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
): State<X>
```
<!-- endSnippet -->

</details>

<details>

<summary><b><code>StorageSetting<T>.asMutableState(...)</code> functions</b></summary>

<!-- snippet: MutableStateExtensions::asMutableState1 -->
```kt
@Composable
fun <T> StorageSetting<T>.asMutableState(): MutableState<T?>
```
<!-- endSnippet -->

<!-- snippet: MutableStateExtensions::asMutableState2 -->
```kt
@Composable
fun <T, X> StorageSetting<T>.asMutableState(
    mapper: (T) -> X,
    unmapper: (X) -> T,
): MutableState<X?>
```
<!-- endSnippet -->

</details>

<details>

<summary><b><code>StorageSetting<T>.asMutableStateNotNull(...)</code> functions</b></summary>

<!-- snippet: MutableStateExtensions::asMutableStateNotNull1 -->
```kt
@Composable
fun <T> StorageSetting<T>.asMutableStateNotNull(): MutableState<T>
```
<!-- endSnippet -->

<!-- snippet: MutableStateExtensions::asMutableStateNotNull2 -->
```kt
@Composable
fun <T : Any, X : Any> StorageSetting<T>.asMutableStateNotNull(
    mapper: (T) -> X,
    unmapper: (X) -> T,
): MutableState<X>
```
<!-- endSnippet -->

</details>

<details>

<summary><b><code>StorageSetting<T>.asMutableStateWithLifecycle(...)</code> functions</b></summary>

<!-- snippet: MutableStateWithLifecycleExtensions::asMutableStateWithLifecycle1 -->
```kt
@Composable
fun <T> StorageSetting<T>.asMutableStateWithLifecycle(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
): MutableState<T?>
```
<!-- endSnippet -->

<!-- snippet: MutableStateWithLifecycleExtensions::asMutableStateWithLifecycle2 -->
```kt
@Composable
fun <T> StorageSetting<T>.asMutableStateWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
): MutableState<T?>
```
<!-- endSnippet -->

<!-- snippet: MutableStateWithLifecycleExtensions::asMutableStateWithLifecycle3 -->
```kt
@Composable
fun <T, X> StorageSetting<T>.asMutableStateWithLifecycle(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
    unmapper: (X) -> T,
): MutableState<X?>
```
<!-- endSnippet -->

<!-- snippet: MutableStateWithLifecycleExtensions::asMutableStateWithLifecycle4 -->
```kt
@Composable
fun <T, X> StorageSetting<T>.asMutableStateWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
    unmapper: (X) -> T,
): MutableState<X?>
```
<!-- endSnippet -->

</details>

<details>

<summary><b><code>StorageSetting<T>.asMutableStateWithLifecycleNotNull(...)</code> functions</b></summary>

<!-- snippet: MutableStateWithLifecycleExtensions::asMutableStateWithLifecycleNotNull1 -->
```kt
@Composable
fun <T> StorageSetting<T>.asMutableStateWithLifecycleNotNull(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
): MutableState<T>
```
<!-- endSnippet -->

<!-- snippet: MutableStateWithLifecycleExtensions::asMutableStateWithLifecycleNotNull2 -->
```kt
@Composable
fun <T> StorageSetting<T>.asMutableStateWithLifecycleNotNull(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
): MutableState<T>
```
<!-- endSnippet -->

<!-- snippet: MutableStateWithLifecycleExtensions::asMutableStateWithLifecycleNotNull3 -->
```kt
@Composable
fun <T : Any, X : Any> StorageSetting<T>.asMutableStateWithLifecycleNotNull(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
    unmapper: (X) -> T,
): MutableState<X>
```
<!-- endSnippet -->

<!-- snippet: MutableStateWithLifecycleExtensions::asMutableStateWithLifecycleNotNull4 -->
```kt
@Composable
fun <T : Any, X : Any> StorageSetting<T>.asMutableStateWithLifecycleNotNull(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    mapper: (T) -> X,
    unmapper: (X) -> T,
): MutableState<X>
```
<!-- endSnippet -->

</details>

<details>

<summary><b><code>StorageSetting<T>.asStateFlow(...)</code> functions</b></summary>

<!-- snippet: StateFlowExtensions::asStateFlow1 -->
```kt
fun <T> StorageSetting<T>.asStateFlow(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(5_000)
): StateFlow<T?>
```
<!-- endSnippet -->

<!-- snippet: StateFlowExtensions::asStateFlow2 -->
```kt
fun <T, T2> StorageSetting<T>.asStateFlow(
    scope: CoroutineScope,
    mapper: (T?) -> T2?,
    started: SharingStarted = SharingStarted.WhileSubscribed(5_000),
): StateFlow<T2?>
```
<!-- endSnippet -->

</details>

<details>

<summary><b><code>StorageSetting<T>.asStateFlowNotNull(...)</code> functions</b></summary>

<!-- snippet: StateFlowExtensions::asStateFlowNotNull1 -->
```kt
fun <T> StorageSetting<T>.asStateFlowNotNull(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(5_000)
): StateFlow<T>
```
<!-- endSnippet -->

<!-- snippet: StateFlowExtensions::asStateFlowNotNull2 -->
```kt
fun <T, T2> StorageSetting<T>.asStateFlowNotNull(
    scope: CoroutineScope,
    mapper: (T) -> T2,
    started: SharingStarted = SharingStarted.WhileSubscribed(5_000),
): StateFlow<T2>
```
<!-- endSnippet -->

</details>
