package com.anandy.motoechargetracker.data.repository

import com.anandy.motoechargetracker.data.source.LocalDataSource
import com.anandy.motoechargetracker.domain.BatteryCharge

class BatteryChargeRepository(private val localDataSource: LocalDataSource) {

    suspend fun getRecords(): List<BatteryCharge> {
        if (localDataSource.isEmpty()) {
            //populateItems().forEach { this.saveCharge(it) }
        }
        return localDataSource.getRecords()
    }

    suspend fun saveCharge(charge: BatteryCharge) {
        val calendar = java.util.Calendar.getInstance()
        calendar.time = charge.date
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
        calendar.set(java.util.Calendar.MINUTE, 0)
        calendar.set(java.util.Calendar.SECOND, 0)
        calendar.set(java.util.Calendar.MILLISECOND, 0)

        charge.date = calendar.time
        if (charge.id == 0) {
            localDataSource.saveCharge(charge)
        } else {
            localDataSource.updateCharge(charge)
        }
    }

    suspend fun remove(charge: BatteryCharge) = localDataSource.remove(charge.id)
    suspend fun getCharge(chargeId: Int): BatteryCharge? = localDataSource.getRecord(chargeId)
    suspend fun removeAllRecords() = localDataSource.removeAllRecords()
}