package com.anandy.motoechargetracker.database

import com.anandy.motoechargetracker.data.source.LocalDataSource
import com.anandy.motoechargetracker.domain.MonthlyCharge
import com.anandy.motoechargetracker.domain.BatteryCharge as DomainCharge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class RoomDataSource(db: BatteryChargeDatabase) : LocalDataSource {

    private val batteryDao: BatteryChargeDao = db.batteryChargeDao()

    override suspend fun isEmpty(): Boolean =
        withContext(Dispatchers.IO) { batteryDao.getRecordsCount() == 0 }

    override suspend fun getRecord(chargeId: Int): DomainCharge? =
        withContext(Dispatchers.IO) { batteryDao.getCharge(chargeId)?.toDomainCharge() }

    override suspend fun getRecords(startDate: String, endDate: String): List<DomainCharge> =
        withContext(Dispatchers.IO) {
            batteryDao.getRecords(startDate, endDate).map { it.toDomainCharge() }
        }

    override suspend fun saveCharge(charge: DomainCharge) =
        withContext(Dispatchers.IO) { batteryDao.saveCharge(setResetChargeIdentifier(charge)) }

    override suspend fun updateCharge(charge: DomainCharge) =
        withContext(Dispatchers.IO) { batteryDao.updateCharge(setResetChargeIdentifier(charge)) }

    override suspend fun remove(chargeId: Int) =
        withContext(Dispatchers.IO) { batteryDao.remove(chargeId) }

    override suspend fun removeAllRecords() =
        withContext(Dispatchers.IO) { batteryDao.removeAllRecords() }

    override suspend fun getMonthlyCharges(): List<MonthlyCharge> =
        withContext(Dispatchers.IO) { batteryDao.getMonthlyCharges() }

    private fun setResetChargeIdentifier(charge: DomainCharge): BatteryCharge {
        var resetId = batteryDao.getCurrentResetId()
        if (charge.resetCharge) {
            resetId++
        }
        return charge.toRoomCharge(resetId)
    }
}