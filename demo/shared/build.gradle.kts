import com.michaelflisar.kmpdevtools.Targets
import com.michaelflisar.kmpdevtools.configs.AndroidLibraryConfig
import com.michaelflisar.kmpdevtools.configs.AppModuleConfig
import com.michaelflisar.kmpdevtools.configs.LibraryModuleConfig
import com.michaelflisar.kmpdevtools.core.Target
import com.michaelflisar.kmpdevtools.setupBuildKonfig
import com.michaelflisar.kmpdevtools.setupDependencies

plugins {
    // kmp + app/library
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    // org.jetbrains.kotlin
    alias(libs.plugins.jetbrains.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    // org.jetbrains.compose
    alias(libs.plugins.jetbrains.compose)
    // docs, publishing, validation
    // --
    // build tools
    alias(mflisar.plugins.kmpdevtools.buildplugin)
    alias(libs.plugins.buildkonfig)
    // others
    // ...
}

// ------------------------
// Setup
// ------------------------

val module = AppModuleConfig.read(project)
val libraryModule = LibraryModuleConfig.read(project)

val buildTargets = Targets(
    // mobile
    Target.ANDROID,
    Target.IOS,
    // desktop
    Target.JVM,
    Target.MACOS,
    // web
    Target.WASM
)

val androidConfig = AndroidLibraryConfig.create(
    libraryModuleConfig = libraryModule,
    compileSdk = app.versions.compileSdk,
    minSdk = app.versions.minSdk,
    enableAndroidResources = true
)

// ------------------------
// Kotlin
// ------------------------

buildkonfig {
    setupBuildKonfig(project = project, config = module.config)
}

compose.resources {
    packageOfResClass = "${module.projectNamespace}.demo.shared.resources"
    publicResClass = true
}

kotlin {

    //-------------
    // Targets
    //-------------

    buildTargets.setupTargetsLibrary(libraryModule)
    android {
        buildTargets.setupTargetsAndroidLibrary(libraryModule, androidConfig, this)
    }

    // ------------------------
    // Sources
    // ------------------------

    sourceSets {

        // ---------------------
        // custom source sets
        // ---------------------

        val iosMain by creating { dependsOn(commonMain.get()) }

        setupDependencies(module, buildTargets, sourceSets) {
            Target.IOS addSourceSet iosMain
        }

        // ---------------------
        // dependencies
        // ---------------------

        commonMain.dependencies {

            // Kotlin
            implementation(libs.jetbrains.kotlinx.coroutines.core)
            implementation(libs.jetbrains.kotlinx.io.core)
            implementation(libs.jetbrains.kotlinx.serialization.json)

            // Compose
            implementation(libs.jetbrains.compose.components.resources)
            implementation(libs.jetbrains.compose.material3)
            implementation(libs.jetbrains.compose.material.icons.core)
            implementation(libs.jetbrains.compose.material.icons.extended)

            // Library
            implementation(project(":kotpreferences:core"))
            implementation(project(":kotpreferences:modules:storage:keyvalue"))
            implementation(project(":kotpreferences:modules:compose"))
        }

        iosMain.dependencies {
            implementation(project(":kotpreferences:modules:storage:datastore"))
        }
    }
}
