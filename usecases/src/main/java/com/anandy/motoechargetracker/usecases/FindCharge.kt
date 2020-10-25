package com.anandy.motoechargetracker.usecases

import com.anandy.motoechargetracker.data.repository.BatteryChargeRepository

class FindCharge(private val batteryChargeRepository: BatteryChargeRepository) {

    suspend fun invoke(chargeId: Int) = batteryChargeRepository.getCharge(chargeId)
}