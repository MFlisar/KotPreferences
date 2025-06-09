import com.michaelflisar.buildlogic.BuildLogicPlugin
import com.michaelflisar.buildlogic.classes.Targets

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.serialization)
    id("com.michaelflisar.buildlogic")
}

// get build logic plugin
val buildLogicPlugin = project.plugins.getPlugin(BuildLogicPlugin::class.java)

// targets
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

val androidNamespace = "com.michaelflisar.kotpreferences.demo.shared"

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


        commonMain.dependencies {

            // Kotlin
            implementation(kotlinx.coroutines.core)
            implementation(kotlinx.io.core)
            implementation(kotlinx.serialization.json)

            // Compose
            implementation(libs.compose.material3)
            implementation(libs.compose.material.icons.core)
            implementation(libs.compose.material.icons.extended)

            // library
            implementation(project(":kotpreferences:core"))
            implementation(project(":kotpreferences:modules:storage:keyvalue"))
            implementation(project(":kotpreferences:modules:compose"))

        }
    }
}

// -------------------
// Configurations
// -------------------

// android configuration
android {
    buildLogicPlugin.setupAndroid(androidNamespace, app.versions.compileSdk, app.versions.minSdk)
}