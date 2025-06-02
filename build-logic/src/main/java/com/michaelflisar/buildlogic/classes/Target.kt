package com.michaelflisar.buildlogic.classes

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