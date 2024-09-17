package com.michaelflisar.kotpreferences.core.classes

enum class SettingsDataType(
    val storageDataType: StorageDataType
) {

    String(StorageDataType.String),
    Boolean(StorageDataType.Boolean),
    Int(StorageDataType.Int),
    Long(StorageDataType.Long),
    Float(StorageDataType.Float),
    Double(StorageDataType.Double),

    StringSet(StorageDataType.StringSet),
    IntSet(StorageDataType.StringSet),
    LongSet(StorageDataType.StringSet),
    FloatSet(StorageDataType.StringSet),
    DoubleSet(StorageDataType.StringSet),

    NullableString(StorageDataType.NullableString),
    NullableBoolean(StorageDataType.NullableBoolean),
    NullableInt(StorageDataType.NullableInt),
    NullableLong(StorageDataType.NullableLong),
    NullableFloat(StorageDataType.NullableFloat),
    NullableDouble(StorageDataType.NullableDouble)
}
