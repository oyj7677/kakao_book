package com.oyj.di.database

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class DbConverter {
    @TypeConverter
    fun fromList(list: List<String>?): String {
        return if (list == null) "" else Json.encodeToString(list)
    }

    @TypeConverter
    fun toList(value: String): List<String> {
        return if (value.isBlank()) emptyList() else Json.decodeFromString(value)
    }
}