package com.michaelflisar.kotpreferences.core.classes

import com.michaelflisar.kotpreferences.core.interfaces.Storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Suppress("UNCHECKED_CAST")
internal fun <T : Any?> Storage.get(type: SettingsDataType, key: String, defaultValue: T): Flow<T> {
    return when (type) {
        SettingsDataType.String -> get(StorageKey(StorageDataType.String, key), defaultValue)
       SettingsDataType.Boolean -> get(StorageKey(StorageDataType.Boolean, key), defaultValue)
        SettingsDataType.Int -> get(StorageKey(StorageDataType.Int, key), defaultValue)
        SettingsDataType.Long -> get(StorageKey(StorageDataType.Long, key), defaultValue)
        SettingsDataType.Float -> get(StorageKey(StorageDataType.Float, key), defaultValue)
        SettingsDataType.Double -> get(StorageKey(StorageDataType.Double, key), defaultValue)
        SettingsDataType.StringSet -> get(StorageKey(StorageDataType.StringSet, key), defaultValue)
        SettingsDataType.IntSet -> get(StorageKey(StorageDataType.StringSet, key), SetConverter.convertIntSetToStringSet(defaultValue as Set<Int>)).map(SetConverter::convertStringToIntSet)
        SettingsDataType.LongSet -> get(StorageKey(StorageDataType.StringSet, key), SetConverter.convertLongSetToStringSet(defaultValue as Set<Long>)).map(SetConverter::convertStringToLongSet)
        SettingsDataType.FloatSet -> get(StorageKey(StorageDataType.StringSet, key), SetConverter.convertFloatSetToStringSet(defaultValue as Set<Float>)).map(SetConverter::convertStringToFloatSet)
        SettingsDataType.DoubleSet -> get(StorageKey(StorageDataType.StringSet, key), SetConverter.convertDoubleSetToStringSet(defaultValue as Set<Double>)).map(SetConverter::convertStringToDoubleSet)
        SettingsDataType.NullableString -> get(StorageKey(StorageDataType.NullableString, key), defaultValue)
        SettingsDataType.NullableBoolean -> get(StorageKey(StorageDataType.NullableBoolean, key), defaultValue)
        SettingsDataType.NullableInt -> get(StorageKey(StorageDataType.NullableInt, key), defaultValue)
        SettingsDataType.NullableLong -> get(StorageKey(StorageDataType.NullableLong, key), defaultValue)
        SettingsDataType.NullableFloat -> get(StorageKey(StorageDataType.NullableFloat, key), defaultValue)
        SettingsDataType.NullableDouble -> get(StorageKey(StorageDataType.NullableDouble, key), defaultValue)
    } as Flow<T>
}

@Suppress("UNCHECKED_CAST")
internal suspend fun <T : Any?> Storage.set(type: SettingsDataType, key: String, value: T) {
    when (type) {
        SettingsDataType.String -> set(StorageKey(StorageDataType.String, key), value)
       SettingsDataType.Boolean -> set(StorageKey(StorageDataType.Boolean, key), value)
        SettingsDataType.Int -> set(StorageKey(StorageDataType.Int, key), value)
        SettingsDataType.Long -> set(StorageKey(StorageDataType.Long, key), value)
        SettingsDataType.Float -> set(StorageKey(StorageDataType.Float, key), value)
        SettingsDataType.Double -> set(StorageKey(StorageDataType.Double, key), value)
        SettingsDataType.StringSet -> set(StorageKey(StorageDataType.StringSet, key), value)
        SettingsDataType.IntSet -> set(StorageKey(StorageDataType.StringSet, key), SetConverter.convertIntSetToStringSet(value as Set<Int>))
        SettingsDataType.LongSet -> set(StorageKey(StorageDataType.StringSet, key), SetConverter.convertLongSetToStringSet(value as Set<Long>))
        SettingsDataType.FloatSet -> set(StorageKey(StorageDataType.StringSet, key), SetConverter.convertFloatSetToStringSet(value as Set<Float>))
        SettingsDataType.DoubleSet -> set(StorageKey(StorageDataType.StringSet, key), SetConverter.convertDoubleSetToStringSet(value as Set<Double>))
        SettingsDataType.NullableString -> set(StorageKey(StorageDataType.NullableString, key), value)
        SettingsDataType.NullableBoolean -> set(StorageKey(StorageDataType.NullableBoolean, key), value)
        SettingsDataType.NullableInt -> set(StorageKey(StorageDataType.NullableInt, key), value)
        SettingsDataType.NullableLong -> set(StorageKey(StorageDataType.NullableLong, key), value)
        SettingsDataType.NullableFloat -> set(StorageKey(StorageDataType.NullableFloat, key), value)
        SettingsDataType.NullableDouble -> set(StorageKey(StorageDataType.NullableDouble, key), value)
    }
}