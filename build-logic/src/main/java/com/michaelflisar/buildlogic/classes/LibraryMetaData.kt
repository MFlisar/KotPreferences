package com.michaelflisar.buildlogic.classes

import org.gradle.api.Project

class LibraryMetaData(
    val library: String,
    val groupId: String,
    val release: Int,
    val github: String,
    val license: String,
) { 
    companion object {
        fun fromGradleProperties(
            project: Project
        ) = LibraryMetaData(
            library = project.property("library") as String,
            groupId = project.property("groupId") as String,
            release = (project.property("release") as String).toInt(),
            github = project.property("github") as String,
            license = project.property("license") as String
        )
    }
}