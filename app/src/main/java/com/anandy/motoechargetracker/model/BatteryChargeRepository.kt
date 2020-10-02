package com.anandy.motoechargetracker.model

import com.anandy.motoechargetracker.BatteryChargeApp
import com.anandy.motoechargetracker.populateItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BatteryChargeRepository(application: BatteryChargeApp) {

    private val db = application.db

    suspend fun getRecords(): List<BatteryCharge> = withContext(Dispatchers.IO){

        with(db.batteryChargeDao()){
            if(getRecordsCount() == 0){
                populateItems().forEach { this.saveCharge(it) }
            }
            getRecords()
        }
    }

    suspend fun saveCharge(charge: BatteryCharge) = withContext(Dispatchers.IO) {
        with(db.batteryChargeDao()) {
            saveCharge(charge)
        }
    }

    suspend fun remove(charge: BatteryCharge) = withContext(Dispatchers.IO) {
        with(db.batteryChargeDao()) {
            remove(charge.id)
        }
    }
}