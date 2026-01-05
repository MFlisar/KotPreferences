plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
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
            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.kotlinx.io.core)

            implementation(libs.compose.material3)

            implementation(libs.compose.ui.test.junit4)

            implementation(project(":kotpreferences:core"))
            implementation(project(":kotpreferences:modules:storage:datastore"))
            implementation(project(":kotpreferences:modules:storage:keyvalue"))
            implementation(project(":kotpreferences:modules:compose"))

        }
    }
}