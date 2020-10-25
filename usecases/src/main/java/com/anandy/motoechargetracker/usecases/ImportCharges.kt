package com.anandy.motoechargetracker.usecases

import com.anandy.motoechargetracker.data.repository.BatteryChargeRepository
import com.anandy.motoechargetracker.domain.BatteryCharge

class ImportCharges(private val batteryChargeRepository: BatteryChargeRepository) {

    suspend fun invoke(fileRecords: List<BatteryCharge>) {
        batteryChargeRepository.removeAllRecords()
        fileRecords.forEach {
            it.id = 0
            batteryChargeRepository.saveCharge(it)
        }
    }
}