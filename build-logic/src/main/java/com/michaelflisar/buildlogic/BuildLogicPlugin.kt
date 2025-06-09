package com.michaelflisar.buildlogic

import com.android.build.gradle.LibraryExtension
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.api.JavaVersion
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

class ModuleMetaData(
    // module
    val artifactId: String,
    val androidNamespace: String,
    // module
    val library: String,
    val description: String,
    val groupID: String,
    val release: Int,
    val github: String,
    val license: String,
) {
    val libraryDescription = "$library - $artifactId module - $description"
    val licenseUrl = "$github/blob/main/LICENSE"
}

enum class Target(
    val targetName: String,
    val targets: List<String>,
) {
    ANDROID("android", listOf("android")),
    IOS("ios", listOf("iosX64", "iosArm64", "iosSimulatorArm64")),
    JVM("jvm", listOf("jvm")),
    MACOS("macos", listOf("macosX64", "macosArm64")),
    LINUX("linux", listOf("linuxX64", "linuxArm64")),
    WASM_JS("wasmJs", listOf("wasmJs")),
    JS("js", listOf("js", "js(IR)"))
    ;

    val nameMain = "${targetName}Main"
}

class Targets(
    val android: Boolean = false,
    val iOS: Boolean = false,
    val windows: Boolean = false,
    val linux: Boolean = false,
    val macOS: Boolean = false,
    val wasm: Boolean = false,
    val js: Boolean = false,
) {
    val enabledTargets = Target.values()
        .filter {
            when (it) {
                Target.ANDROID -> android
                Target.IOS -> iOS
                Target.JVM -> windows
                Target.MACOS -> macOS
                Target.LINUX -> linux
                Target.WASM_JS -> wasm
                Target.JS -> js
            }
        }

    fun updateSourceSetDependencies(
        sourceSets: NamedDomainObjectContainer<KotlinSourceSet>,
        apply: (groupMain: KotlinSourceSet, target: Target) -> Unit,
    ) {
        enabledTargets
            .forEach { target ->
                val groupMain = sourceSets.maybeCreate(target.nameMain)
                apply(groupMain, target)
                target.targets.forEach {
                    sourceSets.getByName("${it}Main").dependsOn(groupMain)
                }
            }
    }
}


class BuildLogicPlugin : Plugin<Project> {

    private lateinit var project: Project

    override fun apply(project: Project) {
        this.project = project
        project.plugins.apply("com.vanniktech.maven.publish.base")
    }

    fun setupMavenPublish(meta: ModuleMetaData, autoPublishReleases: Boolean = true) {
        project.extensions.configure(MavenPublishBaseExtension::class.java) {
            configure(
                KotlinMultiplatform(
                    javadocJar = JavadocJar.Dokka("dokkaHtml"),
                    sourcesJar = true
                )
            )
            coordinates(
                groupId = meta.groupID,
                artifactId = meta.artifactId,
                version = System.getenv("TAG")
            )

            pom {
                name.set(meta.library)
                description.set(meta.libraryDescription)
                inceptionYear.set("${meta.release}")
                url.set(meta.github)

                licenses {
                    license {
                        name.set(meta.license)
                        url.set(meta.licenseUrl)
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
                    url.set(meta.github)
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
                        jvmTarget.set(JvmTarget.JVM_17)
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
        project.extensions.configure(LibraryExtension::class.java) {
            namespace = meta.androidNamespace

            this.compileSdk = compileSdk.get().toInt()

            defaultConfig {
                this.minSdk = minSdk.get().toInt()
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }
}