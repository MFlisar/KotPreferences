import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dokka)
    alias(libs.plugins.gradle.maven.publish.plugin)
}

// -------------------
// Informations
// -------------------

val description = "provides composable extensions"

// Module
val artifactId = "extension-compose"

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

        // ---------------------
        // target sources
        // ---------------------

        androidMain { dependsOn(featureBlocking) }
        //iosMain { dependsOn(featureBlocking) }
        iosX64Main { dependsOn(featureBlocking) }
        iosArm64Main { dependsOn(featureBlocking) }
        iosSimulatorArm64Main { dependsOn(featureBlocking) }
        jvmMain { dependsOn(featureBlocking) }
        //macosMain { dependsOn(featureBlocking) }
        macosX64Main { dependsOn(featureBlocking) }
        macosArm64Main { dependsOn(featureBlocking) }
        //wasmJsMain { dependsOn(featureNoBlocking) }

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

android {
    namespace = "com.michaelflisar.kotpreferences.compose"

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
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, true)

    // Enable GPG signing for all publications
    signAllPublications()
}