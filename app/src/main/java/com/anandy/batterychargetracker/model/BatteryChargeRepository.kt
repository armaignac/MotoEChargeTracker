package com.anandy.batterychargetracker.model

import com.anandy.batterychargetracker.populateItems

class BatteryChargeRepository {

    fun getRecords(): List<BatteryCharge>{
        return populateItems()
    }

    fun saveCharge(charge: BatteryCharge) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}