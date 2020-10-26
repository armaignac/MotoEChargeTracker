package com.anandy.motoechargetracker

import android.app.Application
import androidx.room.Room
import com.anandy.motoechargetracker.database.BatteryChargeDatabase
import com.anandy.motoechargetracker.di.BatteryChargeComponent
import com.anandy.motoechargetracker.di.DaggerBatteryChargeComponent

class BatteryChargeApp : Application() {

    lateinit var component: BatteryChargeComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component = DaggerBatteryChargeComponent
            .factory()
            .create(this)
    }
}