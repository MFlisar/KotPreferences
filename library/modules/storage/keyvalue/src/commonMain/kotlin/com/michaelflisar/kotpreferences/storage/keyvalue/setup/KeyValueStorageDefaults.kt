package com.michaelflisar.kotpreferences.storage.keyvalue.setup

object KeyValueStorageDefaults {
    const val SEPARATOR: String = "="
    val INSET: KeyValueInset = KeyValueBlockInset()
    val CONVERTER = KeyValueConverter(
        separator = SEPARATOR,
        inset = INSET
    )
}