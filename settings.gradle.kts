dependencyResolutionManagement {

    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }

    versionCatalogs {
        create("app") {
            from(files("gradle/app.versions.toml"))
        }
    }

}

pluginManagement {

    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}

// --------------
// Library
// --------------

// KMP ONLY
include(":KotPreferences:Core")
project(":KotPreferences:Core").projectDir = file("library/core")

// --------------
// Modules
// --------------

// Android + JVM + iOS (iOS untested, js missing)
include(":KotPreferences:Modules:Datastore")
project(":KotPreferences:Modules:Datastore").projectDir = file("library/modules/datastore")

// Android + JVM + iOS + js (iOS/js untested)
include(":KotPreferences:Modules:Compose")
project(":KotPreferences:Modules:Compose").projectDir = file("library/modules/compose")

// Android only for now!
include(":KotPreferences:Modules:Encryption:Aes")
project(":KotPreferences:Modules:Encryption:Aes").projectDir = file("library/modules/encryption-aes")

// --------------
// App
// --------------

include(":demo:android")
include(":demo:desktop")