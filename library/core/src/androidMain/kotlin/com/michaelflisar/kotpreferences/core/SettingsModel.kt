package com.michaelflisar.kotpreferences.core

import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting

/**
 * Delegate enum property
 * @param default default enum value
 * @param key custom storage key
 * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
 */
inline fun <reified T : Enum<*>> SettingsModel.enumPref(
    default: T,
    key: String? = null,
    cache: Boolean = storage.cache
): StorageSetting<T> = enumPref(default, T::class.java.enumConstants?.toList() ?: emptyList(), key, cache)
