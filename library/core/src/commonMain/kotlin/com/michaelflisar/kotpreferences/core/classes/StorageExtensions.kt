package com.michaelflisar.kotpreferences.core.classes

import com.michaelflisar.kotpreferences.core.interfaces.Storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Suppress("UNCHECKED_CAST")
internal fun <T : Any?> Storage.get(type: SettingsDataType, key: String, defaultValue: T): Flow<T> {
    return when (type) {
        SettingsDataType.String -> get(StorageDataType.String, key, defaultValue)
        SettingsDataType.Boolean -> get(StorageDataType.Boolean, key, defaultValue)
        SettingsDataType.Int -> get(StorageDataType.Int, key, defaultValue)
        SettingsDataType.Long -> get(StorageDataType.Long, key, defaultValue)
        SettingsDataType.Float -> get(StorageDataType.Float, key, defaultValue)
        SettingsDataType.Double -> get(StorageDataType.Double, key, defaultValue)
        SettingsDataType.StringSet -> get(StorageDataType.StringSet, key, defaultValue)
        SettingsDataType.IntSet -> get(StorageDataType.StringSet, key, SetConverter.convertIntSetToStringSet(defaultValue as Set<Int>)).map(SetConverter::convertStringToIntSet)
        SettingsDataType.LongSet -> get(StorageDataType.StringSet, key, SetConverter.convertLongSetToStringSet(defaultValue as Set<Long>)).map(SetConverter::convertStringToLongSet)
        SettingsDataType.FloatSet -> get(StorageDataType.StringSet, key, SetConverter.convertFloatSetToStringSet(defaultValue as Set<Float>)).map(SetConverter::convertStringToFloatSet)
        SettingsDataType.DoubleSet -> get(StorageDataType.StringSet, key, SetConverter.convertDoubleSetToStringSet(defaultValue as Set<Double>)).map(SetConverter::convertStringToDoubleSet)
        SettingsDataType.NullableString -> get(StorageDataType.NullableString, key, defaultValue)
        SettingsDataType.NullableBoolean -> get(StorageDataType.NullableBoolean, key, defaultValue)
        SettingsDataType.NullableInt -> get(StorageDataType.NullableInt, key, defaultValue)
        SettingsDataType.NullableLong -> get(StorageDataType.NullableLong, key, defaultValue)
        SettingsDataType.NullableFloat -> get(StorageDataType.NullableFloat, key, defaultValue)
        SettingsDataType.NullableDouble -> get(StorageDataType.NullableDouble, key, defaultValue)
    } as Flow<T>
}

@Suppress("UNCHECKED_CAST")
internal suspend fun <T : Any?> Storage.set(type: SettingsDataType, key: String, value: T) {
    when (type) {
        SettingsDataType.String -> set(StorageDataType.String, key, value)
        SettingsDataType.Boolean -> set(StorageDataType.Boolean, key, value)
        SettingsDataType.Int -> set(StorageDataType.Int, key, value)
        SettingsDataType.Long -> set(StorageDataType.Long, key, value)
        SettingsDataType.Float -> set(StorageDataType.Float, key, value)
        SettingsDataType.Double -> set(StorageDataType.Double, key, value)
        SettingsDataType.StringSet -> set(StorageDataType.StringSet, key, value)
        SettingsDataType.IntSet -> set(StorageDataType.StringSet, key, SetConverter.convertIntSetToStringSet(value as Set<Int>))
        SettingsDataType.LongSet -> set(StorageDataType.StringSet, key, SetConverter.convertLongSetToStringSet(value as Set<Long>))
        SettingsDataType.FloatSet -> set(StorageDataType.StringSet, key, SetConverter.convertFloatSetToStringSet(value as Set<Float>))
        SettingsDataType.DoubleSet -> set(StorageDataType.StringSet, key, SetConverter.convertDoubleSetToStringSet(value as Set<Double>))
        SettingsDataType.NullableString -> set(StorageDataType.NullableString, key, value)
        SettingsDataType.NullableBoolean -> set(StorageDataType.NullableBoolean, key, value)
        SettingsDataType.NullableInt -> set(StorageDataType.NullableInt, key, value)
        SettingsDataType.NullableLong -> set(StorageDataType.NullableLong, key, value)
        SettingsDataType.NullableFloat -> set(StorageDataType.NullableFloat, key, value)
        SettingsDataType.NullableDouble -> set(StorageDataType.NullableDouble, key, value)
    }
}