import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")
}

kotlin {

    // Java
    jvm()

    // Android
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    // iOS
    macosX64()
    macosArm64()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    // -------
    // Sources
    // -------

    sourceSets {

        commonMain.dependencies {

            // Kotlin
            implementation(libs.kotlin)
            implementation(libs.kotlinx.coroutines)

            // AndroidX / Google
            implementation(libs.compose.runtime)

            // Library
            implementation(project(":KotPreferences:Core"))
        }

        androidMain.dependencies {
            implementation(libs.androidx.lifecycle.compose)
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

project.afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                artifactId = "compose"
            }
        }
    }
}