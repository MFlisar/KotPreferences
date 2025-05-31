import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
}

kotlin {

    // bei Änderungen folgendes prüfen:
    // - .\src\wasmJsMain\resources\index.html
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName = "demo"
        val rootDirPath = project.rootDir.path
        browser {
            commonWebpackConfig {
                outputFileName = "demo.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets {

        val wasmJsMain by getting {
            dependencies {
                api(libs.compose.material3)

                implementation(project(":kotpreferences:core"))
                implementation(project(":kotpreferences:modules:storage:keyvalue"))
                implementation(project(":kotpreferences:modules:compose"))

            }
        }
    }
}