package com.anandy.motoechargetracker.data.source

import com.anandy.motoechargetracker.domain.BatteryCharge
import com.anandy.motoechargetracker.domain.MonthlyCharge
import java.util.*

interface LocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun getRecord(chargeId: Int): BatteryCharge?
    suspend fun getRecords(startDate: String, endDate: String): List<BatteryCharge>
    suspend fun saveCharge(charge: BatteryCharge)
    suspend fun updateCharge(charge: BatteryCharge)
    suspend fun remove(chargeId: Int)
    suspend fun removeAllRecords()
    suspend fun getMonthlyCharges(): List<MonthlyCharge>
}