package com.MohammedFares.ecomerce_project.data.TypeConverters

import androidx.room.TypeConverter
import java.util.Date

class ConverterDate {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? {
        return millisSinceEpoch?.let { Date(it) }
    }
}