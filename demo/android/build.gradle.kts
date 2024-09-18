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

    implementation(libs.kotlin)
    implementation(libs.kotlinx.serialization.json)

    // ------------------------
    // AndroidX/Compose
    // ------------------------

    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle)

    // Compose
    //implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons.core)
    implementation(libs.compose.material.icons.extended)

    implementation(libs.androidx.activity.compose)

    // simple demo activity helper classes and functions I use in my demo activities
    // + my theming library dependency
    implementation("io.github.mflisar.composedemobaseactivity:library:0.8-alpha02")
    implementation("com.github.MFlisar.ComposeThemer:core:0.2.1")
    implementation("com.github.MFlisar.ComposeThemer:themes:0.2.1")

    // ------------------------
    // Libraries
    // ------------------------

    implementation(project(":KotPreferences:Core"))
    implementation(project(":KotPreferences:Modules:Storage:Datastore"))
    implementation(project(":KotPreferences:Modules:Compose"))
    implementation(project(":KotPreferences:Modules:Encryption:Aes"))
}