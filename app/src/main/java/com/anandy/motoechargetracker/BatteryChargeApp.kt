package com.anandy.motoechargetracker

import android.app.Application
import androidx.room.Room
import com.anandy.motoechargetracker.database.BatteryChargeDatabase

class BatteryChargeApp: Application() {

    lateinit var db: BatteryChargeDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            BatteryChargeDatabase::class.java, "charge-db"
        ).build()
    }
}