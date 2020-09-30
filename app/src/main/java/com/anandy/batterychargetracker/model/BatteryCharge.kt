package com.anandy.batterychargetracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class BatteryCharge(@PrimaryKey(autoGenerate = true) val id: Int, var kilometers: Int, var date: Date) {
}