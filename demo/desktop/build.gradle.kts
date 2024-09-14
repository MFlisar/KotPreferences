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

                //implementation(project(":KotPreferences:Core"))
                //implementation(project(":KotPreferences:Modules:Datastore"))
                //implementation(project(":KotPreferences:Modules:Compose"))

                val kotpreferences = "c1f5c272d6"
                implementation("com.github.MFlisar.KotPreferences:Core:$kotpreferences")
                implementation("com.github.MFlisar.KotPreferences:Datastore:$kotpreferences")
                implementation("com.github.MFlisar.KotPreferences:Compose:$kotpreferences")
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