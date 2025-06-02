import com.michaelflisar.buildlogic.BuildLogicPlugin
import com.michaelflisar.buildlogic.classes.LibraryMetaData
import com.michaelflisar.buildlogic.classes.ModuleMetaData
import com.michaelflisar.buildlogic.classes.Target
import com.michaelflisar.buildlogic.classes.Targets

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.dokka)
    alias(libs.plugins.gradle.maven.publish.plugin)
    alias(libs.plugins.kotlin.serialization)
    id("com.michaelflisar.buildlogic")
}

// get build logic plugin
val buildLogicPlugin = project.plugins.getPlugin(BuildLogicPlugin::class.java)

// -------------------
// Informations
// -------------------

val library = LibraryMetaData.fromGradleProperties(project)
val module = ModuleMetaData(
    artifactId = "storage-keyvalue",
    androidNamespace = "com.michaelflisar.kotpreferences.keyvalue",
    description = "provides a storage implementation based on a simple key-value text file"
)

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

// -------------------
// Variables for Documentation Generator
// -------------------

// # DEP is an optional arrays!

// OPTIONAL = "true"                // defines if this module is optional or not
// GROUP_ID = "storage"             // defines the "grouping" in the documentation this module belongs to
// #DEP = "deps.kotbilling|KotBilling|https://github.com/MFlisar/Kotbilling"
// PLATFORM_INFO = ""               // defines a comment that will be shown in the documentation for this modules platform support

// -------------------
// Setup
// -------------------

kotlin {

    //-------------
    // Targets
    //-------------

    buildLogicPlugin.setupTargets(buildTargets)

    // -------
    // Sources
    // -------

    sourceSets {

        // ---------------------
        // custom shared sources
        // ---------------------

        // all targets but wasm
        val featureFile by creating {
            dependsOn(commonMain.get())
        }

        // wasm only
        //val featureNoFile by creating {
        //    dependsOn(commonMain.get())
        //}

        // ---------------------
        // target sources
        // ---------------------

        // ---------------------
        // target sources
        // ---------------------

        buildTargets.updateSourceSetDependencies(sourceSets) { groupMain, target ->
            when (target) {
                Target.ANDROID, Target.JVM, Target.IOS, Target.MACOS -> {
                    groupMain.dependsOn(featureFile)
                }

                Target.WASM_JS -> {
                    // -
                }

                Target.LINUX,
                Target.JS -> {
                    // not enabled
                }
            }
        }

        // ---------------------
        // dependencies
        // ---------------------

        commonMain.dependencies {

            // Kotlin
            implementation(kotlinx.coroutines.core)
            implementation(kotlinx.io.core)

            // Library
            implementation(project(":kotpreferences:core"))
        }

        androidMain.dependencies {
            implementation(androidx.startup)
        }

        wasmJsMain.dependencies {
            implementation(kotlinx.browser)
            implementation(kotlinx.serialization.json)
        }
    }
}

// -------------------
// Configurations
// -------------------

// android configuration
android {
    buildLogicPlugin.setupAndroid(module, app.versions.compileSdk, app.versions.minSdk)
}

// maven publish configuration
buildLogicPlugin.setupMavenPublish(library, module)