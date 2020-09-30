package com.anandy.batterychargetracker.model

import android.util.Log
import com.anandy.batterychargetracker.BatteryChargeApp
import com.anandy.batterychargetracker.database.BatteryChargeDatabase
import com.anandy.batterychargetracker.populateItems
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

    suspend fun saveCharge(charge: BatteryCharge) =  withContext(Dispatchers.IO){
        with(db.batteryChargeDao()){
            saveCharge(charge)
        }
    }
}