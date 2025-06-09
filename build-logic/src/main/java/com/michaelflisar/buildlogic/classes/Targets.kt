package com.michaelflisar.buildlogic.classes

import org.gradle.api.NamedDomainObjectContainer
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

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