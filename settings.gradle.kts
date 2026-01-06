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
        mavenLocal()
    }
}

// --------------
// Settings Plugin
// --------------

plugins {
    // version catalogue does not work here!
    id("io.github.mflisar.kmpdevtools.plugins-settings-gradle") version "6.3.1"
}

val settingsPlugin = plugins.getPlugin(com.michaelflisar.kmpdevtools.SettingsFilePlugin::class.java)

// --------------
// Library
// --------------

val libraryConfig = com.michaelflisar.kmpdevtools.core.configs.LibraryConfig.read(rootProject)
val libraryId = libraryConfig.library.name.lowercase()

// Library Modules
settingsPlugin.includeModules(libraryId, libraryConfig)

// Library (internal modules)
include(":library:dokka")
include(":library:test")

// --------------
// Demo
// --------------

include(":demo")
include(":demo:shared")
include(":demo:app")