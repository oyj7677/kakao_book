package com.oyj.di.database

import androidx.room.TypeConverter
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

class DbConverter {
    @TypeConverter
    fun fromList(list: List<String>?): String {
        return if (list == null) "" else Json.encodeToString(ListSerializer(String.serializer()), list)
    }

    @TypeConverter
    fun toList(value: String): List<String> {
        return if (value.isBlank()) emptyList() else Json.decodeFromString(ListSerializer(String.serializer()), value)
    }
}