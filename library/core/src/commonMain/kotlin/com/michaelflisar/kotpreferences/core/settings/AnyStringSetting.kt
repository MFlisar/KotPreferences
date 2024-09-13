package com.michaelflisar.kotpreferences.core.settings

import com.michaelflisar.kotpreferences.core.SettingsConverter
import com.michaelflisar.kotpreferences.core.SettingsModel
import com.michaelflisar.kotpreferences.core.interfaces.Storage
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.flow.map
import kotlin.reflect.KProperty

abstract class BaseAnyStringSetting<T : Any?>(
    private val model: com.michaelflisar.kotpreferences.core.SettingsModel,
    override val defaultValue: T,
    override val customKey: String?,
    private val converter: com.michaelflisar.kotpreferences.core.SettingsConverter<T, String>,
    override val cache: Boolean
) : AbstractSetting<T>() {

    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val storage: Storage
        get() = model.storage

    override fun createFlow() =
        model.storage.getString(key, converter.to(defaultValue)).map { converter.from(it) }

    override suspend fun persistValue(value: T) {
        model.storage.setString(key, converter.to(value))
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

class AnyStringSetting<T : Any>(
    model: com.michaelflisar.kotpreferences.core.SettingsModel,
    defaultValue: T,
    customKey: String?,
    converter: com.michaelflisar.kotpreferences.core.SettingsConverter<T, String>,
    cache: Boolean
) : BaseAnyStringSetting<T>(model, defaultValue, customKey, converter, cache)

class NullableAnyStringSetting<T : Any?>(
    model: com.michaelflisar.kotpreferences.core.SettingsModel,
    defaultValue: T?,
    customKey: String?,
    converter: com.michaelflisar.kotpreferences.core.SettingsConverter<T?, String>,
    cache: Boolean
) : BaseAnyStringSetting<T?>(model, defaultValue, customKey, converter, cache)