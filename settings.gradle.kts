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
        //create("plugins") {
        //    from(files("gradle/libs.versions.toml"))
        //}
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

// --------------
// Library
// --------------

// Android + JVM + iOS
include(":kotpreferences:core")
project(":kotpreferences:core").projectDir = file("library/core")

// --------------
// Modules
// --------------

// Android + JVM + iOS (iOS untested)
include(":kotpreferences:modules:storage:datastore")
project(":kotpreferences:modules:storage:datastore").projectDir = file("library/modules/storage/datastore")
include(":kotpreferences:modules:storage:keyvalue")
project(":kotpreferences:modules:storage:keyvalue").projectDir = file("library/modules/storage/keyvalue")

// Android + JVM + iOS (iOS untested)
include(":kotpreferences:modules:compose")
project(":kotpreferences:modules:compose").projectDir = file("library/modules/compose")

// Android
include(":kotpreferences:modules:encryption:aes")
project(":kotpreferences:modules:encryption:aes").projectDir = file("library/modules/encryption-aes")

// --------------
// App
// --------------

include(":test")
include(":demo:android")
include(":demo:windows")
include(":demo:web")