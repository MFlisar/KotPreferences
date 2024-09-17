package com.michaelflisar.kotpreferences.core.classes


sealed interface StorageDataType {

    val base: NotNullable

    sealed interface NotNullable : StorageDataType
    sealed interface Nullable : StorageDataType

    object String : StorageDataType, NotNullable {
        override val base = this
    }

    object Boolean : StorageDataType, NotNullable {
        override val base = this
    }

    object Int : StorageDataType, NotNullable {
        override val base = this
    }

    object Long : StorageDataType, NotNullable {
        override val base = this
    }

    object Float : StorageDataType, NotNullable {
        override val base = this
    }

    object Double : StorageDataType, NotNullable {
        override val base = this
    }

    object StringSet : StorageDataType, NotNullable {
        override val base = this
    }

    object NullableString : StorageDataType, Nullable {
        override val base = String
    }

    object NullableBoolean : StorageDataType, Nullable {
        override val base = Boolean
    }

    object NullableInt : StorageDataType, Nullable {
        override val base = Int
    }

    object NullableLong : StorageDataType, Nullable {
        override val base = Long
    }

    object NullableFloat : StorageDataType, Nullable {
        override val base = Float
    }

    object NullableDouble : StorageDataType, Nullable {
        override val base = Double
    }

}