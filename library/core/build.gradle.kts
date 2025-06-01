import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.dokka)
    alias(libs.plugins.gradle.maven.publish.plugin)
}

// -------------------
// Informations
// -------------------

val description = "provides delegate based preferences"

// Module
val artifactId = "core"
val androidNamespace = "com.michaelflisar.kotpreferences.core"

// Library
val libraryName = "KotPreferences"
val libraryDescription = "KotPreferences - $artifactId module - $description"
val groupID = "io.github.mflisar.kotpreferences"
val release = 2021
val github = "https://github.com/MFlisar/KotPreferences"
val license = "Apache License 2.0"
val licenseUrl = "$github/blob/main/LICENSE"

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
    // Mobile
    //-------------

    // Android
    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    // iOS
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    //-------------
    // Desktop
    //-------------

    // Windows
    jvm()

    // macOS
    macosX64()
    macosArm64()

    // Linux
    // linuxX64()
    // linuxArm64()

    //-------------
    // Web
    //-------------

    // WASM
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        nodejs()
    }

    //-------------
    // JavaScript
    //-------------

    // js()
    // js(IR)

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

        val groupedTargets = mapOf(
            "android" to listOf("android"),
            "ios" to listOf("iosX64", "iosArm64", "iosSimulatorArm64"),
            "jvm" to listOf("jvm"),
            "macos" to listOf("macosX64", "macosArm64"),
            "wasm" to listOf("wasmJs")
        )

        groupedTargets.forEach { group, targets ->
            val groupMain = sourceSets.maybeCreate("${group}Main")
            when (group) {
                "android", "jvm" -> {
                    groupMain.dependsOn(featureIO)
                    groupMain.dependsOn(featureBlocking)
                }
                "ios", "macos" -> {
                    groupMain.dependsOn(featureNoIO)
                    groupMain.dependsOn(featureBlocking)
                }
                "wasm" -> {
                    groupMain.dependsOn(featureNoIO)
                    groupMain.dependsOn(featureNoBlocking)
                }
            }

            targets.forEach { target ->
                sourceSets.getByName("${target}Main").dependsOn(groupMain)
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

android {
    namespace = androidNamespace

    compileSdk = app.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = app.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

mavenPublishing {

    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.Dokka("dokkaHtml"),
            sourcesJar = true
        )
    )

    coordinates(
        groupId = groupID,
        artifactId = artifactId,
        version = System.getenv("TAG")
    )

    pom {
        name.set(libraryName)
        description.set(libraryDescription)
        inceptionYear.set("$release")
        url.set(github)

        licenses {
            license {
                name.set(license)
                url.set(licenseUrl)
            }
        }

        developers {
            developer {
                id.set("mflisar")
                name.set("Michael Flisar")
                email.set("mflisar.development@gmail.com")
            }
        }

        scm {
            url.set(github)
        }
    }

    // Configure publishing to Maven Central
    val autoReleaseOnMavenCentral = providers.gradleProperty("autoReleaseOnMavenCentral").get().toBoolean()
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, autoReleaseOnMavenCentral)

    // Enable GPG signing for all publications
    signAllPublications()
}