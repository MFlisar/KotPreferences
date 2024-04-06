dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
    versionCatalogs {

        val kotlin = "1.9.23"
        val ksp = "1.9.23-1.0.20"
        val coroutines = "1.7.3"
        val json = "1.0.1"
        val gradle = "8.3.1"
        val maven = "2.0"

        // TOML Files
        create("androidx") {
            from(files("gradle/androidx.versions.toml"))
        }
        create("deps") {
            from(files("gradle/dependencies.versions.toml"))
        }
        create("compose") {
            from(files("gradle/compose.versions.toml"))
        }

        // Rest
        create("tools") {
            version("kotlin", kotlin)
            version("gradle", gradle)
            version("maven", maven)
            version("ksp", ksp)
        }
        create("app") {
            version("compileSdk", "34")
            version("minSdk", "21")
            version("targetSdk", "34")
        }
        create("libs") {
            // Kotlin
            library("kotlin", "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin")
            library("kotlin.coroutines", "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines")
            library("kotlin.reflect", "org.jetbrains.kotlin:kotlin-reflect:$kotlin")
            // KotlinX
            library("kotlinx.serialization.json", "org.jetbrains.kotlinx:kotlinx-serialization-json:$json")
        }
    }
}

// --------------
// App
// --------------

include(":KotPreferences:Core")
project(":KotPreferences:Core").projectDir = file("library/core")

include(":KotPreferences:Modules:Datastore")
project(":KotPreferences:Modules:Datastore").projectDir = file("library/modules/datastore")
include(":KotPreferences:Modules:Encryption:Aes")
project(":KotPreferences:Modules:Encryption:Aes").projectDir = file("library/modules/encryption-aes")
include(":KotPreferences:Modules:Compose")
project(":KotPreferences:Modules:Compose").projectDir = file("library/modules/compose")

include(":demo")
project(":demo").projectDir = file("demo")
