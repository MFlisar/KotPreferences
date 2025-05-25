package com.michaelflisar.kotpreferences.core

import com.michaelflisar.kotpreferences.core.classes.EnumConverter
import com.michaelflisar.kotpreferences.core.classes.StorageKey
import com.michaelflisar.kotpreferences.core.interfaces.Storage
import com.michaelflisar.kotpreferences.core.settings.AnyIntSetting
import com.michaelflisar.kotpreferences.core.settings.AnyStringSetting
import com.michaelflisar.kotpreferences.core.settings.DoubleSetting
import com.michaelflisar.kotpreferences.core.settings.FloatSetSetting
import com.michaelflisar.kotpreferences.core.settings.IntSetting
import com.michaelflisar.kotpreferences.core.settings.NullableDoubleSetting
import com.michaelflisar.kotpreferences.core.settings.NullableIntSetting
import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting
import com.michaelflisar.kotpreferences.core.settings.AnyLongSetting
import com.michaelflisar.kotpreferences.core.settings.BoolSetting
import com.michaelflisar.kotpreferences.core.settings.DoubleSetSetting
import com.michaelflisar.kotpreferences.core.settings.FloatSetting
import com.michaelflisar.kotpreferences.core.settings.IntSetSetting
import com.michaelflisar.kotpreferences.core.settings.LongSetSetting
import com.michaelflisar.kotpreferences.core.settings.LongSetting
import com.michaelflisar.kotpreferences.core.settings.NullableAnyIntSetting
import com.michaelflisar.kotpreferences.core.settings.NullableAnyLongSetting
import com.michaelflisar.kotpreferences.core.settings.NullableAnyStringSetting
import com.michaelflisar.kotpreferences.core.settings.NullableBoolSetting
import com.michaelflisar.kotpreferences.core.settings.NullableFloatSetting
import com.michaelflisar.kotpreferences.core.settings.NullableLongSetting
import com.michaelflisar.kotpreferences.core.settings.NullableStringSetting
import com.michaelflisar.kotpreferences.core.settings.StringSetSetting
import com.michaelflisar.kotpreferences.core.settings.StringSetting
import kotlinx.coroutines.flow.Flow

abstract class SettingsModel(
    val storage: Storage
) {
    internal val internalProperties: MutableMap<String, StorageSetting<*>> = mutableMapOf()

    val changes: Flow<SettingsChangeEvent<*>> by lazy {
        storage.changeFlow
    }

    val settings: List<StorageSetting<*>>
        get() = internalProperties.values.toList()

    fun onCreate(setting: StorageSetting<*>) {
        //internalSettings.add(setting)
    }

    suspend fun removeDeprecatedKeysFromFile() {
        val validKeys = internalProperties.map { StorageKey(it.value.storageDataType, it.key) }
        storage.clearDeprecatedKeys(validKeys)
    }

    /**
     * Delegate string property
     * @param default default string value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun stringPref(
        default: String = "",
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<String> = StringSetting(this, default, key, cache)
        .also(::onCreate)

    /**
     * Delegate string property
     * @param default default string value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun nullableStringPref(
        default: String? = null,
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<String?> = NullableStringSetting(this, default, key, cache)
         .also(::onCreate)

    /**
     * Delegate string set property
     * @param default string integer set value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun stringSetPref(
        default: Set<String> = emptySet(),
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<Set<String>> = StringSetSetting(this, default, key, cache)
         .also(::onCreate)

    /**
     * Delegate bool property
     * @param default default bool value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun boolPref(
        default: Boolean = false,
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<Boolean> = BoolSetting(this, default, key, cache)
         .also(::onCreate)

    /**
     * Delegate bool property
     * @param default default bool value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun nullableBoolPref(
        default: Boolean? = null,
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<Boolean?> = NullableBoolSetting(this, default, key, cache)
         .also(::onCreate)

    /**
     * Delegate integer property
     * @param default default integer value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun intPref(
        default: Int = 0,
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<Int> = IntSetting(this, default, key, cache)
         .also(::onCreate)

    /**
     * Delegate integer property
     * @param default default integer value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun nullableIntPref(
        default: Int? = null,
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<Int?> = NullableIntSetting(this, default, key, cache)
         .also(::onCreate)

    /**
     * Delegate integer set property
     * @param default default integer set value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun intSetPref(
        default: Set<Int> = emptySet(),
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<Set<Int>> = IntSetSetting(this, default, key, cache)
         .also(::onCreate)

    /**
     * Delegate float property
     * @param default default float value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun floatPref(
        default: Float = 0f,
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<Float> = FloatSetting(this, default, key, cache)
         .also(::onCreate)

    /**
     * Delegate float property
     * @param default default float value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun nullableFloatPref(
        default: Float? = null,
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<Float?> = NullableFloatSetting(this, default, key, cache)
         .also(::onCreate)

    /**
     * Delegate float set property
     * @param default default float set value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun floatSetPref(
        default: Set<Float> = emptySet(),
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<Set<Float>> = FloatSetSetting(this, default, key, cache)
         .also(::onCreate)

    /**
     * Delegate double property
     * @param default default double value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun doublePref(
        default: Double = 0.0,
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<Double> = DoubleSetting(this, default, key, cache)
         .also(::onCreate)

    /**
     * Delegate double property
     * @param default default doubles value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun nullableDoublePref(
        default: Double? = null,
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<Double?> = NullableDoubleSetting(this, default, key, cache)
         .also(::onCreate)

    /**
     * Delegate double set property
     * @param default default double set value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun doubleSetPref(
        default: Set<Double> = emptySet(),
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<Set<Double>> = DoubleSetSetting(this, default, key, cache)
         .also(::onCreate)

    /**
     * Delegate long property
     * @param default default long value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun longPref(
        default: Long = 0L,
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<Long> = LongSetting(this, default, key, cache)
         .also(::onCreate)

    /**
     * Delegate long property
     * @param default default long value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun nullableLongPref(
        default: Long? = null,
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<Long?> = NullableLongSetting(this, default, key, cache)
         .also(::onCreate)

    /**
     * Delegate long property
     * @param default default long value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun longSetPref(
        default: Set<Long> = emptySet(),
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<Set<Long>> = LongSetSetting(this, default, key, cache)
         .also(::onCreate)

    /**
     * Delegate enum property
     * @param default default enum value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    inline fun <reified T : Enum<*>> enumPref(
        default: T,
        entries: List<T>,
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<T> = AnyIntSetting(this, default, key, EnumConverter(entries), cache)
         .also(::onCreate)

    // --------------------------
    // ANY Settings
    // --------------------------

    /**
     * Delegate any property
     * @param default default any value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun <T : Any> anyStringPref(
        converter: com.michaelflisar.kotpreferences.core.SettingsConverter<T, String>,
        default: T,
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<T> = AnyStringSetting(this, default, key, converter, cache)
         .also(::onCreate)

    /**
     * Delegate any property
     * @param default default any value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun <T : Any?> nullableAnyStringPref(
        converter: com.michaelflisar.kotpreferences.core.SettingsConverter<T?, String>,
        default: T?,
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<T?> = NullableAnyStringSetting(this, default, key, converter, cache)
        .also(::onCreate)

    /**
     * Delegate any property
     * @param default default any value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun <T : Any> anyIntPref(
        converter: com.michaelflisar.kotpreferences.core.SettingsConverter<T, Int>,
        default: T,
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<T> = AnyIntSetting(this, default, key, converter, cache)
        .also(::onCreate)

    /**
     * Delegate any property
     * @param default default any value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun <T : Any?> nullableAnyIntPref(
        converter: com.michaelflisar.kotpreferences.core.SettingsConverter<T?, Int>,
        default: T?,
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<T?> = NullableAnyIntSetting(this, default, key, converter, cache)
        .also(::onCreate)

    /**
     * Delegate any property
     * @param default default any value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun <T : Any> anyLongPref(
        converter: com.michaelflisar.kotpreferences.core.SettingsConverter<T, Long>,
        default: T,
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<T> = AnyLongSetting(this, default, key, converter, cache)
        .also(::onCreate)

    /**
     * Delegate any property
     * @param default default any value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun <T : Any?> nullableAnyLongPref(
        converter: com.michaelflisar.kotpreferences.core.SettingsConverter<T?, Long>,
        default: T?,
        key: String? = null,
        cache: Boolean = storage.cache
    ): StorageSetting<T?> = NullableAnyLongSetting(this, default, key, converter, cache)
        .also(::onCreate)
}