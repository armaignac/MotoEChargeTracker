package com.anandy.motoechargetracker.database

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    private val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    @TypeConverter
    fun toDate(strDate: String): Date {
        return format.parse(strDate)!!
    }

    @TypeConverter
    fun fromDate(date: Date): String {

        return format.format(date)
    }
}