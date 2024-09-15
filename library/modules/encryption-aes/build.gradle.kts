import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.JavadocJar

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    //id("maven-publish")
    id("org.jetbrains.dokka") version "1.9.20"
    id("com.vanniktech.maven.publish") version "0.29.0"
}

kotlin {

    // Java
    // jvm()

    // Android
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    // iOS
    // macosX64()
    // macosArm64()
    // iosArm64()
    // iosX64()
    // iosSimulatorArm64()

    // -------
    // Sources
    // -------

    sourceSets {

        commonMain.dependencies {

            // Kotlin
            implementation(libs.kotlin)

            // Library
            implementation(project(":KotPreferences:Core"))
        }
    }
}

android {
    namespace = "com.michaelflisar.kotpreferences.encryption.aes"

    compileSdk = app.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = app.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

/*
project.afterEvaluate {
    publishing {
        publications {
            withType<MavenPublication> {
                val updated = artifactId.replaceFirst(project.name, "encryption-aes")
                println("artifactId = $artifactId => $updated")
                artifactId = updated
            }
        }
    }
}*/

// Setup for release
val artifactId = "kotpreferences-encryption-aes"