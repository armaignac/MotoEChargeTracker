package com.anandy.motoechargetracker.domain

import java.util.*

data class BatteryCharge(
    var id: Int = 0,
    var kilometers: Int,
    var date: Date,
    var resetCharge: Boolean = false
)