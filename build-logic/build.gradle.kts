plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
}

gradlePlugin {
    plugins {
        create("BuildLogicPlugin") {
            id = "com.michaelflisar.buildlogic"
            implementationClass = "com.michaelflisar.buildlogic.BuildLogicPlugin"
        }
    }
}

dependencies {
    implementation(libs.gradle.maven.publish.plugin)
    implementation(libs.kotlin.multiplatform)
    implementation(libs.android.build.tools)
}