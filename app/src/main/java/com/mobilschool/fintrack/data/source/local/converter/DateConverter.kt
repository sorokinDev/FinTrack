package com.mobilschool.fintrack.data.source.local.converter

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time ?: 0

    @TypeConverter
    fun timestampToDate(timestamp: Long): Date = Date(timestamp)

}