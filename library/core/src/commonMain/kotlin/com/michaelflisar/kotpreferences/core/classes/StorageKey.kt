package com.michaelflisar.kotpreferences.core.classes

import kotlin.jvm.JvmInline

@JvmInline
value class StorageKey<T: StorageDataType>(
    val data: Pair<String, T>
) {
    constructor(type: T, key: String) : this(Pair(key, type))

    val key: String
        get() = data.first
    val type: T
        get() = data.second
}