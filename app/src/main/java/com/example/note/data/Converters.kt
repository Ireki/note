package com.example.note.data

import androidx.room.TypeConverter
import java.util.*

@Suppress("DEPRECATION")
class Converters {
    @TypeConverter
    fun fromTimestamp(value: String?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): String? {
        return date?.time?.toString()
    }
}