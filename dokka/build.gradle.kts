import com.michaelflisar.kmpdevtools.core.configs.LibraryConfig
import com.michaelflisar.kmpdevtools.core.utils.ModuleUtil

plugins {
    kotlin("jvm") apply false
    alias(libs.plugins.dokka)
}

val libraryConfig = LibraryConfig.read(rootProject)
val libraryId = libraryConfig.library.name.lowercase()

dependencies {
    libraryConfig.modules
        .filter { it.artifactId.isNotEmpty() }
        .forEach {
            val moduleName = ModuleUtil.folderToModuleName(it.path, libraryId)
            dokka(project(moduleName))
        }
}

dokka {
    moduleName.set(libraryConfig.library.name)
}
