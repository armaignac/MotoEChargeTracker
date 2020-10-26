package com.anandy.motoechargetracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class BatteryCharge(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var kilometers: Int,
    var date: Date
) {
}