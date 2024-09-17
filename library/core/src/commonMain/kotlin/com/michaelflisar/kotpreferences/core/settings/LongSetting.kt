package com.michaelflisar.kotpreferences.core.settings

import com.michaelflisar.kotpreferences.core.SettingsModel
import com.michaelflisar.kotpreferences.core.classes.SettingsDataType
import com.michaelflisar.kotpreferences.core.classes.get
import com.michaelflisar.kotpreferences.core.classes.set
import com.michaelflisar.kotpreferences.core.interfaces.Storage
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

internal abstract class BaseLongSetting<T : Long?>(
    private val model: SettingsModel,
    override val settingsDataType: SettingsDataType
) : AbstractSetting<T>() {

    private var name: String? = null
    override val key: String by lazy { customKey ?: name!! }

    override val storage: Storage
        get() = model.storage

    override fun createFlow() = model.storage.get(settingsDataType, key, defaultValue)

    override suspend fun persistValue(value: T) {
        model.storage.set(settingsDataType, key, value)
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

internal class LongSetting(
    model: SettingsModel,
    override val defaultValue: Long,
    override val customKey: String?,
    override val cache: Boolean
) : BaseLongSetting<Long>(model, SettingsDataType.Long)

internal class NullableLongSetting(
    model: SettingsModel,
    override val defaultValue: Long?,
    override val customKey: String?,
    override val cache: Boolean
) : BaseLongSetting<Long?>(model, SettingsDataType.NullableLong)