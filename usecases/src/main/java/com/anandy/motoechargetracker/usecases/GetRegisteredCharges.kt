package com.anandy.motoechargetracker.usecases

import com.anandy.motoechargetracker.data.repository.BatteryChargeRepository

class GetRegisteredCharges(private val batteryChargeRepository: BatteryChargeRepository) {

    suspend fun invoke(startDate: String, endDate: String) =
        batteryChargeRepository.getRecords(startDate, endDate)
}