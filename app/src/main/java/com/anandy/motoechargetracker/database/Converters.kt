package com.anandy.motoechargetracker.database

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {

    companion object {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    }

    @TypeConverter
    fun toDate(strDate: String): Date {
        return dateFormat.parse(strDate)!!
    }

    @TypeConverter
    fun fromDate(date: Date): String {

        return dateFormat.format(date)
    }
}