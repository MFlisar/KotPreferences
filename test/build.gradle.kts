plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {

    // Java
    jvm()

    // -------
    // Sources
    // -------

    sourceSets {

        commonMain.dependencies {

            // Kotlin
            implementation(kotlinx.coroutines.core)

            implementation(libs.kotlin.test)
            implementation(kotlinx.coroutines.test)
            implementation(kotlinx.io.core)

            implementation(project(":kotpreferences:core"))
            implementation(project(":kotpreferences:modules:storage:datastore"))
            implementation(project(":kotpreferences:modules:storage:keyvalue"))

        }
    }
}