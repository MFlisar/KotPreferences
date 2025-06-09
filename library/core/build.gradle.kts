import com.michaelflisar.buildlogic.BuildLogicPlugin
import com.michaelflisar.buildlogic.ModuleMetaData
import com.michaelflisar.buildlogic.Target
import com.michaelflisar.buildlogic.Targets

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.dokka)
    alias(libs.plugins.gradle.maven.publish.plugin)
    id("com.michaelflisar.buildlogic")
}

// get build logic plugin
val buildLogicPlugin = project.plugins.getPlugin(BuildLogicPlugin::class.java)

// -------------------
// Informations
// -------------------

val meta = ModuleMetaData(
    // module
    artifactId = "core",
    androidNamespace = "com.michaelflisar.kotpreferences.core",
    // library
    library = "KotPreferences",
    description = "provides delegate based preferences",
    groupID = "io.github.mflisar.kotpreferences",
    release = 2021,
    github = "https://github.com/MFlisar/KotPreferences",
    license = "Apache License 2.0"
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

// # DEP + GROUP are optional arrays!

// OPTIONAL = "false"               // defines if this module is optional or not
// GROUP_ID = "core"                // defines the "grouping" in the documentation this module belongs to
// #DEP = "deps.composables.core|Compose Unstyled (core)|https://github.com/composablehorizons/compose-unstyled/"
// PLATFORM_INFO = ""               // defines a comment that will be shown in the documentation for this modules platform support

// GLOBAL DATA
// BRANCH = "master"        // defines the branch on github (master/main)
// GROUP = "core|Core|core"
// GROUP = "storage|Storage|select a storage implementation"
// GROUP = "extensions|Extensions|optional extensions"
// GROUP = "encryptions|Encryption|optionally add additional encryption"

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
        val featureBlocking by creating {
            dependsOn(commonMain.get())
        }

        // wasm only
        val featureNoBlocking by creating {
            dependsOn(commonMain.get())
        }

        // all targets but ios + wasm
        val featureIO by creating {
            dependsOn(commonMain.get())
        }

        // ios + wasm only
        val featureNoIO by creating {
            dependsOn(commonMain.get())
        }

        // ---------------------
        // target sources
        // ---------------------

        buildTargets.updateSourceSetDependencies(sourceSets) { groupMain, target ->
            when (target) {
                Target.ANDROID, Target.JVM -> {
                    groupMain.dependsOn(featureIO)
                    groupMain.dependsOn(featureBlocking)
                }

                Target.IOS, Target.MACOS -> {
                    groupMain.dependsOn(featureNoIO)
                    groupMain.dependsOn(featureBlocking)
                }

                Target.WASM_JS -> {
                    groupMain.dependsOn(featureNoIO)
                    groupMain.dependsOn(featureNoBlocking)
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

        }

        androidMain.dependencies {
            implementation(kotlinx.coroutines.android)
            implementation(androidx.startup)
        }

        jvmMain.dependencies {
            implementation(kotlinx.coroutines.swing)
        }
    }
}

// -------------------
// Configurations
// -------------------

// android configuration
android {
    buildLogicPlugin.setupAndroid(meta, app.versions.compileSdk, app.versions.minSdk)
}

// maven publish configuration
buildLogicPlugin.setupMavenPublish(meta = meta)