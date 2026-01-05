import com.codingfeline.buildkonfig.compiler.FieldSpec.Type
import com.michaelflisar.kmpdevtools.BuildFileUtil
import com.michaelflisar.kmpdevtools.Targets
import com.michaelflisar.kmpdevtools.config.AppModuleData
import com.michaelflisar.kmpdevtools.config.sub.AndroidAppConfig
import com.michaelflisar.kmpdevtools.config.sub.DesktopAppConfig
import com.michaelflisar.kmpdevtools.config.sub.WasmAppConfig
import com.michaelflisar.kmpdevtools.core.Platform
import com.michaelflisar.kmpdevtools.core.configs.Config
import com.michaelflisar.kmpdevtools.core.configs.LibraryConfig

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.hotreload)
    alias(libs.plugins.buildkonfig)
    alias(deps.plugins.kmpdevtools.buildplugin)
}

// ------------------------
// Setup
// ------------------------

val config = Config.read(rootProject)
val libraryConfig = LibraryConfig.read(rootProject)

val buildTargets = Targets(
    // mobile
    android = true,
    iOS = true,
    // desktop
    windows = true,
    macOS = true,
    // web
    wasm = true
)

val androidConfig = AndroidAppConfig(
    compileSdk = app.versions.compileSdk,
    minSdk = app.versions.minSdk,
    targetSdk = app.versions.targetSdk
)

val desktopConfig = DesktopAppConfig(
    mainClass = "com.michaelflisar.kotpreferences.demo.MainKt",
    ico = "icon.ico"
)

val wasmConfig = WasmAppConfig(
    moduleName = "demo",
    outputFileName = "demo.js"
)

val appModuleData = AppModuleData(
    project = project,
    config = config,
    appName = "${libraryConfig.library.name} Demo",
    namespace = "com.michaelflisar.kotpreferences.demo",
    versionName = "1.0.0",
    versionCode = 1,
    androidConfig = androidConfig,
    desktopConfig = desktopConfig,
    wasmConfig = wasmConfig
)

// ------------------------
// Kotlin
// ------------------------

buildkonfig {
    packageName = appModuleData.namespace
    defaultConfigs {
        buildConfigField(Type.STRING, "versionName", appModuleData.versionName)
        buildConfigField(Type.INT, "versionCode", appModuleData.versionCode.toString())
        buildConfigField(Type.STRING, "packageName", appModuleData.namespace)
        buildConfigField(Type.STRING, "appName", appModuleData.appName)
    }
}

kotlin {

    //-------------
    // Targets
    //-------------

    buildTargets.setupTargetsApp(appModuleData)

    // ------------------------
    // Source Sets
    // ------------------------

    sourceSets {

        // ---------------------
        // custom source sets
        // ---------------------

        val iosMain by creating { dependsOn(commonMain.get()) }
        val featureDatastoreMain by creating { dependsOn(commonMain.get()) }

        buildTargets.setupDependencies(iosMain, sourceSets, buildTargets, listOf(Platform.IOS))
        buildTargets.setupDependencies(featureDatastoreMain, sourceSets, buildTargets, listOf(Platform.WASM), platformsNotSupported = true)

        // ------------------------
        // dependencies
        // ------------------------

        commonMain.dependencies {

            // AndroidX/Compose/Material
            implementation(libs.compose.material3)
            implementation(libs.compose.material.icons.core)
            implementation(libs.compose.material.icons.extended)

            // Modules
            implementation(project(":demo:shared"))

            // Library
            implementation(project(":kotpreferences:core"))
            implementation(project(":kotpreferences:modules:storage:keyvalue"))
            implementation(project(":kotpreferences:modules:compose"))

        }

        featureDatastoreMain.dependencies {
            implementation(project(":kotpreferences:modules:storage:datastore"))
        }

        androidMain.dependencies {

            // AndroidX/Compose/Material
            implementation(libs.androidx.activity.compose)

            // Library
            implementation(project(":kotpreferences:modules:storage:datastore"))
            implementation(project(":kotpreferences:modules:encryption-aes"))

        }


        jvmMain.dependencies {

            implementation(compose.desktop.currentOs) {
                exclude(group = "org.jetbrains.compose.material", module = "material")
            }

        }
    }
}

// -------------------
// Configurations
// -------------------

// android configuration
android {

    BuildFileUtil.setupAndroidApp(
        appModuleData = appModuleData,
        buildConfig = true,
        generateResAppName = true,
        checkDebugKeyStoreProperty = true,
        setupBuildTypesDebugAndRelease = true
    )
}

// windows configuration
compose.desktop {
    application {
        BuildFileUtil.setupWindowsApp(
            application = this,
            appModuleData = appModuleData
        )
    }
}
