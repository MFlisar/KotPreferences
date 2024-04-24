dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
    versionCatalogs {
        create("androidx") {
            from(files("gradle/androidx.versions.toml"))
        }
        create("deps") {
            from(files("gradle/dependencies.versions.toml"))
        }
        create("compose") {
            from(files("gradle/compose.versions.toml"))
        }
        create("app") {
            from(files("gradle/app.versions.toml"))
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
