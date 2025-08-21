dependencyResolutionManagement {

    // repositories for dependencies
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://jitpack.io")
        mavenLocal()
    }

    versionCatalogs {
        create("app") {
            from(files("gradle/app.versions.toml"))
        }
        create("androidx") {
            from(files("gradle/androidx.versions.toml"))
        }
        create("kotlinx") {
            from(files("gradle/kotlinx.versions.toml"))
        }
        create("deps") {
            from(files("gradle/deps.versions.toml"))
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
// Functions
// --------------

fun includeModule(path: String, name: String) {
    include(name)
    project(name).projectDir = file(path)
}

// --------------
// Library
// --------------

includeModule("library/core", ":kotpreferences:core")
includeModule("library/modules/storage/datastore", ":kotpreferences:modules:storage:datastore")
includeModule("library/modules/storage/keyvalue", ":kotpreferences:modules:storage:keyvalue")
includeModule("library/modules/compose", ":kotpreferences:modules:compose")
includeModule("library/modules/encryption-aes", ":kotpreferences:modules:encryption:aes")

// --------------
// Demo
// --------------

include(":demo:shared")
include(":demo:app:android")
include(":demo:app:windows")
include(":demo:app:web")

// Test
include(":test")