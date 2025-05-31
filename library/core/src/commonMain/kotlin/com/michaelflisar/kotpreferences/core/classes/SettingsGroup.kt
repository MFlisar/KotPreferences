package com.michaelflisar.kotpreferences.core.classes

import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged

class SettingsGroup<T>(
    internal vararg val settings: StorageSetting<*>,
    private val distinctUntilChanged: Boolean = true,
    internal val converter: (List<Any?>) -> T
) {
    val flow by lazy {
        val f = combine(settings.map { it.flow }) {
            converter(it.toList())
        }
        if (distinctUntilChanged)
            f.distinctUntilChanged()
        else
            f
    }

    suspend fun reset(): List<StorageSetting<*>> {
        val settingsToReset = ArrayList<StorageSetting<*>>()
        settings.forEach {
            if (it.reset())
                settingsToReset.add(it)
        }
        return settingsToReset
    }
}