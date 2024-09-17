import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.dokka)
    alias(libs.plugins.gradle.maven.publish.plugin)
}

kotlin {

    // Java
    jvm()

    // Android
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    // iOS
    macosX64()
    macosArm64()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    // -------
    // Sources
    // -------

    sourceSets {

        commonMain.dependencies {

            // Kotlin
            implementation(libs.kotlin)
            implementation(libs.kotlinx.coroutines)

            // AndroidX / Google
            implementation(libs.androidx.datastore.preferences)

            // Library
            implementation(project(":KotPreferences:Core"))
        }
    }
}

android {
    namespace = "com.michaelflisar.kotpreferences.datastore"

    compileSdk = app.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = app.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

/*
project.afterEvaluate {
    publishing {
        publications {
            withType<MavenPublication> {
                val updated = artifactId.replaceFirst(project.name, "datastore")
                println("artifactId = $artifactId => $updated")
                artifactId = updated
            }
        }
    }
}*/

// Setup for release
val groupID = "io.github.mflisar.kotpreferences"
val artifactId = "datastore"
mavenPublishing {

    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.Dokka("dokkaHtml"),
            sourcesJar = true
        )
    )

    // Define coordinates for the published artifact
    coordinates(
        groupId = groupID,
        artifactId = artifactId,
        version = System.getenv("TAG")
    )

    // Configure POM metadata for the published artifact
    pom {
        name.set("KotPreferences")
        description.set("With this library you can declare preferences via kotlin delegates and observe and update them via kotlin Flows. This works with any storage implementation, an implementation for JetPack DataStore is provided already.")
        inceptionYear.set("2024")
        url.set("https://github.com/MFlisar/KotPreferences")

        licenses {
            license {
                name.set("Apache License 2.0")
                url.set("https://github.com/MFlisar/KotPreferences/blob/main/LICENSE")
            }
        }

        // Specify developer information
        developers {
            developer {
                id.set("mflisar")
                name.set("Michael Flisar")
                email.set("mflisar.development@gmail.com")
            }
        }

        // Specify SCM information
        scm {
            url.set("https://github.com/MFlisar/KotPreferences")
        }
    }

    // Configure publishing to Maven Central
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    // Enable GPG signing for all publications
    signAllPublications()
}