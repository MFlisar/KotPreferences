import com.michaelflisar.kmpdevtools.BuildFileUtil
import com.michaelflisar.kmpdevtools.Targets
import com.michaelflisar.kmpdevtools.configs.*
import com.michaelflisar.kmpdevtools.core.Target
import com.michaelflisar.kmpdevtools.setupDependencies

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
    alias(mflisar.plugins.kmpdevtools.buildplugin)
    // others
    // ...
}

// ------------------------
// Setup
// ------------------------

val module = LibraryModuleConfig.read(project)

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
    libraryModuleConfig = module,
    compileSdk = app.versions.compileSdk,
    minSdk = app.versions.minSdk,
    enableAndroidResources = false
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

    buildTargets.setupTargetsLibrary(module)
    android {
        buildTargets.setupTargetsAndroidLibrary(module, androidConfig, this)
    }

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
        val featureFile by creating { dependsOn(commonMain.get()) }


        setupDependencies(module, buildTargets, sourceSets) {

            featureBlocking supportedBy !Target.WASM
            featureNoBlocking supportedBy Target.WASM
            featureIO supportedBy Target.LIST_FILE_SUPPORT
            featureNoIO supportedBy !Target.LIST_FILE_SUPPORT
            featureFile supportedBy Target.LIST_FILE_SUPPORT

        }

        // ---------------------
        // dependencies
        // ---------------------

        commonMain.dependencies {

            // Kotlin
            implementation(libs.jetbrains.kotlinx.coroutines.core)

        }

        featureFile.dependencies {
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

BuildFileUtil.setupMavenPublish(module)