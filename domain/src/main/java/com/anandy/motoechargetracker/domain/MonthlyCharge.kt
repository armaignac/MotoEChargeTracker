package com.anandy.motoechargetracker.domain

import java.util.*

data class MonthlyCharge(
    var monthDate: Date,
    var monthGroup: String,
    var totalKilometers: Int,
    var totalCharges: Int,
    var lastKilometers: Int = 0
)