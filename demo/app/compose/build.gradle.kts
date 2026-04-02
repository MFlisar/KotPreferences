import com.michaelflisar.kmpdevtools.Targets
import com.michaelflisar.kmpdevtools.BuildFileUtil
import com.michaelflisar.kmpdevtools.core.Platform
import com.michaelflisar.kmpdevtools.configs.library.AndroidLibraryConfig
import com.michaelflisar.kmpdevtools.configs.app.DesktopAppConfig
import com.michaelflisar.kmpdevtools.configs.app.WasmAppConfig
import com.michaelflisar.kmpdevtools.configs.module.AppModuleConfig
import com.michaelflisar.kmpdevtools.setupDependencies

plugins {
    // kmp + app/library
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    // org.jetbrains.kotlin
    alias(libs.plugins.jetbrains.kotlin.compose)
    // org.jetbrains.compose
    alias(libs.plugins.jetbrains.compose)
    // docs, publishing, validation
    // --
    // build tools
    alias(deps.plugins.kmpdevtools.buildplugin)
    // others
    // ...
}

// ------------------------
// Setup
// ------------------------

val module = AppModuleConfig.readManual(project)

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

val androidConfig = AndroidLibraryConfig.createFromPath(
    appModuleConfig = module,
    compileSdk = app.versions.compileSdk,
    minSdk = app.versions.minSdk,
    enableAndroidResources = true
)

val desktopConfig = DesktopAppConfig(
    mainClass = "com.michaelflisar.kotpreferences.demo.MainKt",
    ico = "icon.ico"
)

val wasmConfig = WasmAppConfig(
    moduleName = "demo",
    outputFileName = "demo.js"
)

// ------------------------
// Kotlin
// ------------------------

kotlin {

    //-------------
    // Targets
    //-------------

    buildTargets.setupTargetsApp(module, wasmAppConfig = wasmConfig)
    android {
        buildTargets.setupTargetsAndroidLibrary(module, androidConfig, this)
    }

    // ------------------------
    // Source Sets
    // ------------------------

    sourceSets {

        // ---------------------
        // custom source sets
        // ---------------------

        val iosMain by creating { dependsOn(commonMain.get()) }
        val featureDatastoreMain by creating { dependsOn(commonMain.get()) }

        setupDependencies(buildTargets, sourceSets) {

            Platform.IOS addSourceSet iosMain

            featureDatastoreMain supportedBy !Platform.WASM

        }

        // ------------------------
        // dependencies
        // ------------------------

        commonMain.dependencies {

            // AndroidX/Compose/Material
            implementation(libs.jetbrains.compose.material3)
            implementation(libs.jetbrains.compose.material.icons.core)
            implementation(libs.jetbrains.compose.material.icons.extended)

            // Modules
            api(project(":demo:shared"))

            // Library
            implementation(project(":kotpreferences:core"))
            implementation(project(":kotpreferences:modules:storage:keyvalue"))
            implementation(project(":kotpreferences:modules:compose"))

        }

        featureDatastoreMain.dependencies {
            implementation(project(":kotpreferences:modules:storage:datastore"))
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

// windows configuration
compose.desktop {
    application {
        BuildFileUtil.setupWindowsApp(
            appModuleConfig = module,
            application = this,
            desktopAppConfig = desktopConfig
        )
    }
}