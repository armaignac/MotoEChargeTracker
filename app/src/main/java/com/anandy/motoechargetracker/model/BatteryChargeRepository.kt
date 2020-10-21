package com.anandy.motoechargetracker.model

import com.anandy.motoechargetracker.BatteryChargeApp
import com.anandy.motoechargetracker.populateItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class BatteryChargeRepository(application: BatteryChargeApp) {

    private val db = application.db

    suspend fun getRecords(): List<BatteryCharge> = withContext(Dispatchers.IO){

        with(db.batteryChargeDao()){
            if(getRecordsCount() == 0){
               //populateItems().forEach { this.saveCharge(it) }
            }
            getRecords()
        }
    }

    suspend fun saveCharge(charge: BatteryCharge) = withContext(Dispatchers.IO) {
        with(db.batteryChargeDao()) {

            val calendar = Calendar.getInstance()
            calendar.time = charge.date
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            charge.date = calendar.time
            if (charge.id == 0) {
                saveCharge(charge)
            } else {
                updateCharge(charge)
            }
        }
    }

    suspend fun remove(charge: BatteryCharge) = withContext(Dispatchers.IO) {
        with(db.batteryChargeDao()) {
            remove(charge.id)
        }
    }

    suspend fun getCharge(chargeId: Int): BatteryCharge? = withContext(Dispatchers.IO) {
        with(db.batteryChargeDao()) {
            getCharge(chargeId)
        }
    }

    suspend fun removeAllRecords() = withContext(Dispatchers.IO) {
        with(db.batteryChargeDao()) {
            removeAllRecords()
        }
    }
}