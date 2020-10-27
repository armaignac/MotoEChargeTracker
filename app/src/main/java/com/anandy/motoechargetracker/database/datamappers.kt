package com.anandy.motoechargetracker.database

import com.anandy.motoechargetracker.domain.BatteryCharge
import com.anandy.motoechargetracker.database.BatteryCharge as RoomCharge

fun BatteryCharge.toRoomCharge(resetId: Int): RoomCharge = RoomCharge(id, kilometers, date, resetId)

fun RoomCharge.toDomainCharge(): BatteryCharge = BatteryCharge(id, kilometers, chargeDate)