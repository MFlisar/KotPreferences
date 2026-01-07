plugins {
    // kmp + app/library
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    // org.jetbrains.kotlin
    alias(libs.plugins.jetbrains.kotlin.compose)
    // org.jetbrains.compose
    alias(libs.plugins.jetbrains.compose)
    // docs, publishing, validation
    // --
    // build tools
    // ...
    // others
    // ...
}

kotlin {

    // Java
    jvm()

    // -------
    // Sources
    // -------

    sourceSets {

        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }

        commonMain.dependencies {

            // Kotlin
            implementation(libs.jetbrains.kotlinx.coroutines.core)

            implementation(libs.kotlin.test)
            implementation(libs.jetbrains.kotlinx.coroutines.test)
            implementation(libs.jetbrains.kotlinx.io.core)

            implementation(libs.jetbrains.compose.material3)

            implementation(libs.jetbrains.compose.ui.test.junit4)

            implementation(project(":kotpreferences:core"))
            implementation(project(":kotpreferences:modules:storage:datastore"))
            implementation(project(":kotpreferences:modules:storage:keyvalue"))
            implementation(project(":kotpreferences:modules:compose"))

        }
    }
}