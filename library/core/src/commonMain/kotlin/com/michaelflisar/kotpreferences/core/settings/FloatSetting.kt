package com.michaelflisar.kotpreferences.core.settings

import com.michaelflisar.kotpreferences.core.SettingsModel
import com.michaelflisar.kotpreferences.core.interfaces.Storage
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlin.reflect.KProperty

internal abstract class BaseFloatSetting<T : Float?>(
    private val model: com.michaelflisar.kotpreferences.core.SettingsModel
) : AbstractSetting<T>() {

    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val storage: Storage
        get() = model.storage

    override fun createFlow() = model.storage.getFloat(key, defaultValue)

    override suspend fun persistValue(value: T) {
        model.storage.setFloat(key, value)
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
    ): StorageSetting<T> {
        init(property.name)
        return this
    }
}

internal class FloatSetting(
    model: com.michaelflisar.kotpreferences.core.SettingsModel,
    override val defaultValue: Float,
    override val customKey: String?,
    override val cache: Boolean
) : BaseFloatSetting<Float>(model)

internal class NullableFloatSetting(
    model: com.michaelflisar.kotpreferences.core.SettingsModel,
    override val defaultValue: Float?,
    override val customKey: String?,
    override val cache: Boolean
) : BaseFloatSetting<Float?>(model)