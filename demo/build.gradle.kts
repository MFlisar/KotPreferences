plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
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

    composeOptions {
        kotlinCompilerExtensionVersion = compose.versions.compiler.get()
    }
}

dependencies {

    // ------------------------
    // Kotlin
    // ------------------------

    implementation(libs.kotlin)

    implementation(libs.kotlinx.serialization.json)

    // ------------------------
    // AndroidX/Compose
    // ------------------------

    implementation(androidx.core)
    implementation(androidx.lifecycle)

    // Compose BOM
    implementation(platform(compose.bom))
    implementation(compose.material3)
    implementation(compose.material.extendedicons)

    implementation(compose.activity)
    //implementation(compose.drawablepainter)

    // ------------------------
    // Libraries
    // ------------------------

    implementation(project(":KotPreferences:Core"))
    implementation(project(":KotPreferences:Modules:Datastore"))
    implementation(project(":KotPreferences:Modules:Encryption:Aes"))
    implementation(project(":KotPreferences:Modules:Compose"))
}