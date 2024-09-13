package com.michaelflisar.kotpreferences.core.settings

import com.michaelflisar.kotpreferences.core.SettingsModel
import com.michaelflisar.kotpreferences.core.interfaces.Storage
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlin.reflect.KProperty

internal class DoubleSetSetting(
    private val model: com.michaelflisar.kotpreferences.core.SettingsModel,
    override val defaultValue: Set<Double>,
    override val customKey: String?,
    override val cache: Boolean
) : AbstractSetting<Set<Double>>() {

    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val storage: Storage
        get() = model.storage

    override fun createFlow() = model.storage.getDoubleSet(key, defaultValue)

    override suspend fun persistValue(value: Set<Double>) {
        model.storage.setDoubleSet(key, value)
    }

    private fun init(name: String) {
        if (this.name == null) {
            this.name = name
            model.internalProperties[key] = this
        }
    }

    /* Delegate */
    override fun getValue(
        thisRef: com.michaelflisar.kotpreferences.core.SettingsModel,
        property: KProperty<*>
    ): StorageSetting<Set<Double>> {
        init(property.name)
        return this
    }
}