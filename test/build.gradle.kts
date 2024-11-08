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
            implementation(kotlinx.coroutines)

            implementation(libs.kotlin.test)
            implementation(kotlinx.coroutines.test)

            implementation(project(":KotPreferences:Core"))
            implementation(project(":KotPreferences:Modules:Storage:Datastore"))
            implementation(project(":KotPreferences:Modules:Storage:KeyValue"))
            //implementation(project(":KotPreferences:Modules:Compose"))

        }
    }
}