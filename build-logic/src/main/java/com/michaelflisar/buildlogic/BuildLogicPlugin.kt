package com.michaelflisar.buildlogic

import com.android.build.gradle.LibraryExtension
import com.michaelflisar.buildlogic.classes.LibraryMetaData
import com.michaelflisar.buildlogic.classes.ModuleMetaData
import com.michaelflisar.buildlogic.classes.Targets
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

private val JAVA_VERSION = JavaVersion.VERSION_17
private val JAVA_TARGET = JvmTarget.JVM_17

class BuildLogicPlugin : Plugin<Project> {

    private lateinit var project: Project

    override fun apply(project: Project) {
        this.project = project
        //project.plugins.apply("com.vanniktech.maven.publish.base")
    }

    fun setupMavenPublish(
        library: LibraryMetaData,
        module: ModuleMetaData,
        autoPublishReleases: Boolean = true
    ) {
        project.extensions.configure(MavenPublishBaseExtension::class.java) {
            configure(
                KotlinMultiplatform(
                    javadocJar = JavadocJar.Dokka("dokkaHtml"),
                    sourcesJar = true
                )
            )
            coordinates(
                groupId = library.groupID,
                artifactId = module.artifactId,
                version = System.getenv("TAG")
            )

            pom {
                name.set(library.library)
                description.set(module.libraryDescription(library))
                inceptionYear.set("${library.release}")
                url.set(library.github)

                licenses {
                    license {
                        name.set(library.license)
                        url.set(module.licenseUrl(library))
                    }
                }

                developers {
                    developer {
                        id.set("mflisar")
                        name.set("Michael Flisar")
                        email.set("mflisar.development@gmail.com")
                    }
                }

                scm {
                    url.set(library.github)
                }
            }

            // Configure publishing to Maven Central
            val tag = System.getenv("TAG").orEmpty() // is set by the github action workflow
            val autoReleaseOnMavenCentral =
                autoPublishReleases && !tag.contains("-") // debug, alpha and test builds end like "-debug", "-alpha", "-test" and should not be released automatically
            publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, autoReleaseOnMavenCentral)

            // Enable GPG signing for all publications
            signAllPublications()
        }
    }

    fun setupTargets(
        targets: Targets,
    ) {
        project.extensions.configure(KotlinMultiplatformExtension::class.java) {

            // Android
            if (targets.android) {
                androidTarget {
                    publishLibraryVariants("release")
                    compilerOptions {
                        jvmTarget.set(JAVA_TARGET)
                    }
                }
            }

            // iOS
            if (targets.iOS) {
                iosX64()
                iosArm64()
                iosSimulatorArm64()
            }

            // Windows
            if (targets.windows) {
                jvm()
            }

            // macOS
            if (targets.macOS) {
                macosX64()
                macosArm64()
            }

            // Linux
            if (targets.linux) {
                linuxX64()
                linuxArm64()
            }

            // WASM
            if (targets.wasm) {
                @OptIn(ExperimentalWasmDsl::class)
                wasmJs {
                    nodejs()
                }
            }

            // JavaScript
            if (targets.js) {
                js()
                js(IR)
            }
        }
    }

    fun setupAndroid(meta: ModuleMetaData, compileSdk: Provider<String>, minSdk: Provider<String>) {
        setupAndroid(meta.androidNamespace, compileSdk, minSdk)
    }

    fun setupAndroid(
        androidNamespace: String,
        compileSdk: Provider<String>,
        minSdk: Provider<String>
    ) {
        project.extensions.configure(LibraryExtension::class.java) {
            namespace = androidNamespace

            this.compileSdk = compileSdk.get().toInt()

            defaultConfig {
                this.minSdk = minSdk.get().toInt()
            }

            compileOptions {
                sourceCompatibility = JAVA_VERSION
                targetCompatibility = JAVA_VERSION
            }
        }
    }
}