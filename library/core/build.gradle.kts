import com.michaelflisar.kmplibrary.BuildFilePlugin
import com.michaelflisar.kmplibrary.setupDependencies
import com.michaelflisar.kmplibrary.Target
import com.michaelflisar.kmplibrary.Targets

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.dokka)
    alias(libs.plugins.gradle.maven.publish.plugin)
    alias(libs.plugins.binary.compatibility.validator)
    alias(deps.plugins.kmplibrary.buildplugin)
}

// get build file plugin
val buildFilePlugin = project.plugins.getPlugin(BuildFilePlugin::class.java)

// -------------------
// Informations
// -------------------

val androidNamespace = "com.michaelflisar.kotpreferences.core"

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
// Setup
// -------------------

kotlin {

    //-------------
    // Targets
    //-------------

    buildFilePlugin.setupTargetsLibrary(buildTargets)

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

        featureBlocking.setupDependencies(sourceSets, buildTargets,listOf(Target.WASM), targetsNotSupported = true)
        featureNoBlocking.setupDependencies(sourceSets, buildTargets, listOf(Target.WASM))
        featureIO.setupDependencies(sourceSets, buildTargets, Target.LIST_FILE_SUPPORT
        featureNoIO.setupDependencies(sourceSets, buildTargets, Target.LIST_FILE_SUPPORT, targetsNotSupported = true)

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
    buildFilePlugin.setupAndroidLibrary(
        androidNamespace = androidNamespace,
        compileSdk = app.versions.compileSdk,
        minSdk = app.versions.minSdk,
        buildConfig = false
    )
}

// maven publish configuration
if (buildFilePlugin.checkGradleProperty("publishToMaven") != false)
    buildFilePlugin.setupMavenPublish()




