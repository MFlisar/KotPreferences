plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
}

android {

    namespace = "com.michaelflisar.kotpreferences.demo"

    compileSdk = app.versions.compileSdk.get().toInt()

    buildFeatures {
        compose = true
    }

    defaultConfig {
        minSdk = app.versions.minSdk.get().toInt()
        targetSdk = app.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {

    // ------------------------
    // Kotlin
    // ------------------------

    implementation(kotlinx.serialization.json)

    // ------------------------
    // AndroidX/Compose
    // ------------------------

    implementation(androidx.core)
    implementation(androidx.lifecycle)

    // Compose
    //implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons.core)
    implementation(libs.compose.material.icons.extended)

    implementation(androidx.activity.compose)

    // ------------------------
    // Modules
    // ------------------------

    implementation(project(":demo:shared"))

    // ------------------------
    // Libraries
    // ------------------------

    implementation(project(":kotpreferences:core"))
    implementation(project(":kotpreferences:modules:storage:datastore"))
    implementation(project(":kotpreferences:modules:compose"))
    implementation(project(":kotpreferences:modules:encryption:aes"))
}