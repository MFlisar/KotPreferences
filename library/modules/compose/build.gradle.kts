import com.michaelflisar.buildlogic.BuildLogicPlugin
import com.michaelflisar.buildlogic.classes.LibraryMetaData
import com.michaelflisar.buildlogic.classes.ModuleMetaData
import com.michaelflisar.buildlogic.classes.Targets

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dokka)
    alias(libs.plugins.gradle.maven.publish.plugin)
    id("com.michaelflisar.buildlogic")
}

// get build logic plugin
val buildLogicPlugin = project.plugins.getPlugin(BuildLogicPlugin::class.java)

// -------------------
// Informations
// -------------------

val library = LibraryMetaData.fromGradleProperties(project)
val module = ModuleMetaData(
    artifactId = "extension-compose",
    androidNamespace = "com.michaelflisar.kotpreferences.compose",
    description = "provides composable extensions"
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
// GROUP_ID = "extensions"             // defines the "grouping" in the documentation this module belongs to
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
        // dependencies
        // ---------------------

        commonMain.dependencies {

            // Kotlin
            implementation(kotlinx.coroutines.core)

            // AndroidX / Google
            implementation(libs.compose.runtime)

            // Library
            implementation(project(":kotpreferences:core"))
        }

        androidMain.dependencies {
            implementation(androidx.lifecycle.compose)
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