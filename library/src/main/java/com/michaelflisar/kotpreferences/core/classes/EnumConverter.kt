package com.michaelflisar.kotpreferences.core.classes

import com.michaelflisar.kotpreferences.core.SettingsConverter

class EnumConverter<T : Enum<*>>(
        enumClass: Class<T>
) : SettingsConverter<T, Int> {
    private val enumConstants = enumClass.enumConstants

    override fun from(data: Int): T = enumConstants.first { it.ordinal == data }
    override fun to(data: T) = data.ordinal
}