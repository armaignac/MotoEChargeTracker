package com.anandy.batterychargetracker.model

import com.anandy.batterychargetracker.populateItems

class BatteryChargeRepository {

    fun getRecords(): List<BatteryCharge>{
        return populateItems()
    }
}