pluginManagement {

    // repositories for build
    repositories {
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {

    // repositories for dependencies
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

// --------------
// Library
// --------------

// Android + JVM + iOS
include(":KotPreferences:Core")
project(":KotPreferences:Core").projectDir = file("library/core")

// --------------
// Modules
// --------------

// Android + JVM + iOS (iOS untested)
include(":KotPreferences:Modules:Storage:Datastore")
project(":KotPreferences:Modules:Storage:Datastore").projectDir = file("library/modules/storage/datastore")
include(":KotPreferences:Modules:Storage:KeyValue")
project(":KotPreferences:Modules:Storage:KeyValue").projectDir = file("library/modules/storage/keyvalue")

// Android + JVM + iOS (iOS untested)
include(":KotPreferences:Modules:Compose")
project(":KotPreferences:Modules:Compose").projectDir = file("library/modules/compose")

// Android
include(":KotPreferences:Modules:Encryption:Aes")
project(":KotPreferences:Modules:Encryption:Aes").projectDir = file("library/modules/encryption-aes")

// --------------
// App
// --------------

include(":test")
include(":demo:android")
include(":demo:desktop")