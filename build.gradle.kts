plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.compose.hotreload) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.gradle.maven.publish.plugin) apply false
    alias(libs.plugins.buildkonfig) apply false
    alias(deps.plugins.kmpdevtools.buildplugin)
}

// ----------------------------
// Apply custom build file plugin
// ----------------------------

// provided gradle tasks in root project:
// * updateMarkdownFiles
// * macActions
// * renameProject
buildFilePlugin {

    // do not build demo projects in CI
    excludeDemoFromCI.set(true)
}