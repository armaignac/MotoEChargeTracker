package com.anandy.motoechargetracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anandy.motoechargetracker.model.BatteryCharge
import com.anandy.motoechargetracker.model.Converters

@Database(entities = [BatteryCharge::class], version = 1)
@TypeConverters(value = [Converters::class])
abstract class BatteryChargeDatabase : RoomDatabase(){

    abstract fun batteryChargeDao(): BatteryChargeDao
}