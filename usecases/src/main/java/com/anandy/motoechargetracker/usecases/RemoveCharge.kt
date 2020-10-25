package com.anandy.motoechargetracker.usecases

import com.anandy.motoechargetracker.data.repository.BatteryChargeRepository
import com.anandy.motoechargetracker.domain.BatteryCharge

class RemoveCharge(private val batteryChargeRepository: BatteryChargeRepository) {

    suspend fun invoke(charge: BatteryCharge) = batteryChargeRepository.remove(charge)
}