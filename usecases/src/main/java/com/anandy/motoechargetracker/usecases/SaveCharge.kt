package com.anandy.motoechargetracker.usecases

import com.anandy.motoechargetracker.data.repository.BatteryChargeRepository
import com.anandy.motoechargetracker.domain.BatteryCharge

class SaveCharge(private val batteryChargeRepository: BatteryChargeRepository) {

    suspend fun invoke(charge: BatteryCharge) = batteryChargeRepository.saveCharge(charge)
}