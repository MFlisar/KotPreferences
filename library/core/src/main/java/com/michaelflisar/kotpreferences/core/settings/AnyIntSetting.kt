package com.michaelflisar.kotpreferences.core.settings

import com.michaelflisar.kotpreferences.core.SettingsConverter
import com.michaelflisar.kotpreferences.core.SettingsModel
import com.michaelflisar.kotpreferences.core.interfaces.Storage
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.flow.map
import kotlin.reflect.KProperty

abstract class BaseAnyIntSetting<T : Any?>(
    private val model: SettingsModel,
    override val defaultValue: T,
    override val customKey: String?,
    val converter: SettingsConverter<T, Int>,
    override val cache: Boolean
) : AbstractSetting<T>() {

    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val storage: Storage
        get() = model.storage

    override fun createFlow() =
        model.storage.getInt(key, converter.to(defaultValue)).map { converter.from(it) }

    override suspend fun persistValue(value: T) {
        model.storage.setInt(key, converter.to(value))
    }

    private fun init(name: String) {
        if (this.name == null) {
            this.name = name
            model.internalProperties[key] = this
        }
    }

    /* Delegate */
    override fun getValue(
        thisRef: SettingsModel,
        property: KProperty<*>
    ): StorageSetting<T> {
        init(property.name)
        return this
    }
}

class AnyIntSetting<T : Any>(
    model: SettingsModel,
    defaultValue: T,
    customKey: String?,
    converter: SettingsConverter<T, Int>,
    cache: Boolean
) : BaseAnyIntSetting<T>(model, defaultValue, customKey, converter, cache)

class NullableAnyIntSetting<T : Any?>(
    model: SettingsModel,
    defaultValue: T?,
    customKey: String?,
    converter: SettingsConverter<T?, Int>,
    cache: Boolean
) : BaseAnyIntSetting<T?>(model, defaultValue, customKey, converter, cache)