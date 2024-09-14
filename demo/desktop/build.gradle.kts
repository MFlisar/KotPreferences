import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
}

kotlin {

    jvm {
        withJava()
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {

                implementation(compose.desktop.currentOs)

                implementation(project(":KotPreferences:Core"))
                implementation(project(":KotPreferences:Modules:Datastore"))
                implementation(project(":KotPreferences:Modules:Compose"))
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.michaelflisar.kotpreferences.demo.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Exe)
            packageName = "KotPreference JVM Demo"
            packageVersion = "1.0.0"
        }
    }
}