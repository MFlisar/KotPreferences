import com.michaelflisar.kmpdevtools.core.configs.LibraryConfig

plugins {
    kotlin("jvm") apply false
    alias(libs.plugins.dokka)
}

dependencies {
    dokka(project(":kotpreferences:core"))
    dokka(project(":kotpreferences:modules:storage:datastore"))
    dokka(project(":kotpreferences:modules:storage:keyvalue"))
    dokka(project(":kotpreferences:modules:compose"))
    dokka(project(":kotpreferences:modules:encryption-aes"))
}

dokka {
    val libraryConfig = LibraryConfig.read(rootProject)
    moduleName.set(libraryConfig.library.name)
}
