package com.anandy.motoechargetracker.database

import com.anandy.motoechargetracker.domain.BatteryCharge
import com.anandy.motoechargetracker.database.BatteryCharge as RoomCharge

fun BatteryCharge.toRoomCharge(): RoomCharge = RoomCharge(id, kilometers, date)

fun RoomCharge.toDomainCharge(): BatteryCharge = BatteryCharge(id, kilometers, date)