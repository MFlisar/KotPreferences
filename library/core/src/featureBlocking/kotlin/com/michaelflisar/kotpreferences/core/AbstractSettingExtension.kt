package com.michaelflisar.kotpreferences.core

import com.michaelflisar.kotpreferences.core.settings.AbstractSetting
import com.michaelflisar.kotpreferences.core.classes.SettingsGroup
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

/*
 * blocking getter that nearly always hits the cache for convenience
 */
val <T : Any?> StorageSetting<T>.value: T
    get() {
        val c = getCache()
        return if (c != null)
            c.data
        else
            runBlocking(Dispatchers.IO) { flow.first() }
    }

val <T> SettingsGroup<T>.value
    get() = converter(settings.map { (it as AbstractSetting<*>).value })