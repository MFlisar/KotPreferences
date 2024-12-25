plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {

    // Java
    jvm {
        withJava()
    }

    // Android
    //androidTarget {
    //    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    //    compilerOptions {
    //        jvmTarget.set(JvmTarget.JVM_17)
    //    }
    //}

    // iOS
    //macosX64()
    //macosArm64()
    //iosArm64()
    //iosX64()
    //iosSimulatorArm64()

    // -------
    // Sources
    // -------

    sourceSets {

        commonMain.dependencies {

            // Kotlin
            implementation(kotlinx.coroutines.core)

            implementation(libs.kotlin.test)
            implementation(kotlinx.coroutines.test)

            implementation(project(":kotpreferences:core"))
            implementation(project(":kotpreferences:modules:storage:datastore"))
            implementation(project(":kotpreferences:modules:storage:keyvalue"))
            //implementation(project(":KotPreferences:Modules:Compose"))

        }
    }
}