import com.michaelflisar.kmpdevtools.BuildFileUtil
import com.michaelflisar.kmpdevtools.Targets
import com.michaelflisar.kmpdevtools.config.LibraryModuleData
import com.michaelflisar.kmpdevtools.config.sub.AndroidLibraryConfig
import com.michaelflisar.kmpdevtools.core.Platform
import com.michaelflisar.kmpdevtools.core.configs.Config
import com.michaelflisar.kmpdevtools.core.configs.LibraryConfig

plugins {
    // kmp + app/library
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    // org.jetbrains.kotlin
    // --
    // org.jetbrains.compose
    // --
    // docs, publishing, validation
    alias(libs.plugins.dokka)
    alias(libs.plugins.vanniktech.maven.publish.base)
    alias(libs.plugins.binary.compatibility.validator)
    // build tools
    alias(deps.plugins.kmpdevtools.buildplugin)
    // others
    // ...
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

val androidConfig = AndroidLibraryConfig(
    compileSdk = app.versions.compileSdk,
    minSdk = app.versions.minSdk
)

val libraryModuleData = LibraryModuleData(
    project = project,
    config = config,
    libraryConfig = libraryConfig,
    androidConfig = androidConfig
)

// ------------------------
// Kotlin
// ------------------------

kotlin {

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    //-------------
    // Targets
    //-------------

    buildTargets.setupTargetsLibrary(libraryModuleData)

    // -------
    // Sources
    // -------

    sourceSets {

        // ---------------------
        // custom source sets
        // ---------------------

        val featureBlocking by creating { dependsOn(commonMain.get()) }
        val featureNoBlocking by creating { dependsOn(commonMain.get()) }
        val featureIO by creating { dependsOn(commonMain.get()) }
        val featureNoIO by creating { dependsOn(commonMain.get()) }

        buildTargets.setupDependencies(featureBlocking, sourceSets, buildTargets, listOf(Platform.WASM), platformsNotSupported = true)
        buildTargets.setupDependencies(featureNoBlocking, sourceSets, buildTargets, listOf(Platform.WASM))
        buildTargets.setupDependencies(featureIO, sourceSets, buildTargets, Platform.LIST_FILE_SUPPORT)
        buildTargets.setupDependencies(featureNoIO, sourceSets, buildTargets, Platform.LIST_FILE_SUPPORT, platformsNotSupported = true)

        // ---------------------
        // dependencies
        // ---------------------

        commonMain.dependencies {

            // Kotlin
            implementation(libs.jetbrains.kotlinx.coroutines.core)
            implementation(libs.jetbrains.kotlinx.io.core)

        }

        androidMain.dependencies {
            implementation(libs.jetbrains.kotlinx.coroutines.android)
            implementation(libs.androidx.startup.runtime)
        }

        jvmMain.dependencies {
            implementation(libs.jetbrains.kotlinx.coroutines.swing)
        }
    }
}

// -------------------
// Publish
// -------------------

// maven publish configuration
if (BuildFileUtil.checkGradleProperty(project, "publishToMaven") != false)
    BuildFileUtil.setupMavenPublish(project, config, libraryConfig)