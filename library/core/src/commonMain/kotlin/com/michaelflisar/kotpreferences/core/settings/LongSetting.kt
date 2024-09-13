package com.michaelflisar.kotpreferences.core.settings

import com.michaelflisar.kotpreferences.core.SettingsModel
import com.michaelflisar.kotpreferences.core.interfaces.Storage
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlin.reflect.KProperty

internal abstract class BaseLongSetting<T : Long?>(
    private val model: com.michaelflisar.kotpreferences.core.SettingsModel
) : AbstractSetting<T>() {

    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val storage: Storage
        get() = model.storage

    override fun createFlow() = model.storage.getLong(key, defaultValue)

    override suspend fun persistValue(value: T) {
        model.storage.setLong(key, value)
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

internal class LongSetting(
    model: com.michaelflisar.kotpreferences.core.SettingsModel,
    override val defaultValue: Long,
    override val customKey: String?,
    override val cache: Boolean
) : BaseLongSetting<Long>(model)

internal class NullableLongSetting(
    model: com.michaelflisar.kotpreferences.core.SettingsModel,
    override val defaultValue: Long?,
    override val customKey: String?,
    override val cache: Boolean
) : BaseLongSetting<Long?>(model)