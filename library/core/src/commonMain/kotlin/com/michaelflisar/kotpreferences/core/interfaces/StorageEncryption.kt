package com.michaelflisar.kotpreferences.core.interfaces

import com.michaelflisar.kotpreferences.core.classes.StorageDataType

interface StorageEncryption {
    fun <T> decrypt(data: String, type: StorageDataType.NotNullable): T
    fun <T> encrypt(value: T, type: StorageDataType.NotNullable): String
}