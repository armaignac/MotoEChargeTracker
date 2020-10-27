package com.anandy.motoechargetracker.database

import android.util.Log
import com.anandy.motoechargetracker.data.source.LocalDataSource
import com.anandy.motoechargetracker.domain.BatteryCharge as DomainCharge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(db: BatteryChargeDatabase) : LocalDataSource {

    private val batteryDao: BatteryChargeDao = db.batteryChargeDao()

    override suspend fun isEmpty(): Boolean =
        withContext(Dispatchers.IO) { batteryDao.getRecordsCount() == 0 }

    override suspend fun getRecord(chargeId: Int): DomainCharge? =
        withContext(Dispatchers.IO) { batteryDao.getCharge(chargeId)?.toDomainCharge() }

    override suspend fun getRecords(): List<DomainCharge> =
        withContext(Dispatchers.IO) {
            batteryDao.getRecords().map { it.toDomainCharge() }
        }

    override suspend fun saveCharge(charge: DomainCharge) =
        withContext(Dispatchers.IO) { batteryDao.saveCharge(setResetChargeIdentifier(charge)) }

    override suspend fun updateCharge(charge: DomainCharge) =
        withContext(Dispatchers.IO) { batteryDao.updateCharge(setResetChargeIdentifier(charge)) }

    override suspend fun remove(chargeId: Int) =
        withContext(Dispatchers.IO) { batteryDao.remove(chargeId) }

    override suspend fun removeAllRecords() =
        withContext(Dispatchers.IO) { batteryDao.removeAllRecords() }

    private fun setResetChargeIdentifier(charge: DomainCharge): BatteryCharge {
        var resetId = batteryDao.getCurrentResetId()
        if (charge.resetCharge) {
            resetId++
        }
        return charge.toRoomCharge(resetId)
    }
}