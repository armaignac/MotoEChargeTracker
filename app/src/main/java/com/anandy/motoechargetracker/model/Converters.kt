package com.anandy.motoechargetracker.model

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun toDate(dateLong:Long):Date {
        return Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date):Long{
        return date.time
    }
}