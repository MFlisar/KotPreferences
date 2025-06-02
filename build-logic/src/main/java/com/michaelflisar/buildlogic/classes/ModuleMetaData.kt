package com.michaelflisar.buildlogic.classes

class ModuleMetaData(
    val artifactId: String,
    val androidNamespace: String,
    val description: String
) {
    fun libraryDescription(library: LibraryMetaData) =
        "$library - $artifactId module - $description"

    fun licenseUrl(library: LibraryMetaData) = "${library.github}/blob/main/LICENSE"
}