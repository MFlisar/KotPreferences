package com.michaelflisar.kotpreferences.core

interface SettingsConverter<T, Data> {
    fun from(data: Data): T
    fun to(data: T): Data
}