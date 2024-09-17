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

                val kotpreferences = null//"0.6.0-alpha01"
                if (kotpreferences != null) {
                    implementation("io.github.mflisar:kotpreferences-core:$kotpreferences")
                    implementation("io.github.mflisar:kotpreferences-datastore:$kotpreferences")
                    implementation("io.github.mflisar:kotpreferences-compose:$kotpreferences")
                } else {
                    implementation(project(":KotPreferences:Core"))
                    implementation(project(":KotPreferences:Modules:Storage:Datastore"))
                    implementation(project(":KotPreferences:Modules:Storage:KeyValue"))
                    implementation(project(":KotPreferences:Modules:Compose"))

                }
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