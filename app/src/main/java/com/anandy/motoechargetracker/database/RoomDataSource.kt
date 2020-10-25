package com.anandy.motoechargetracker.database

import com.anandy.motoechargetracker.data.source.LocalDataSource
import com.anandy.motoechargetracker.domain.BatteryCharge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(db: BatteryChargeDatabase) : LocalDataSource {

    private val batteryDao: BatteryChargeDao = db.batteryChargeDao()

    override suspend fun isEmpty(): Boolean =
        withContext(Dispatchers.IO) { batteryDao.getRecordsCount() == 0 }

    override suspend fun getRecord(chargeId: Int): BatteryCharge? =
        withContext(Dispatchers.IO) { batteryDao.getCharge(chargeId)?.toDomainCharge() }

    override suspend fun getRecords(): List<BatteryCharge> =
        withContext(Dispatchers.IO) { batteryDao.getRecords().map { it.toDomainCharge() } }

    override suspend fun saveCharge(charge: BatteryCharge) =
        withContext(Dispatchers.IO) { batteryDao.saveCharge(charge.toRoomCharge()) }

    override suspend fun updateCharge(charge: BatteryCharge) =
        withContext(Dispatchers.IO) { batteryDao.updateCharge(charge.toRoomCharge()) }

    override suspend fun remove(chargeId: Int) =
        withContext(Dispatchers.IO) { batteryDao.remove(chargeId) }

    override suspend fun removeAllRecords() =
        withContext(Dispatchers.IO) { batteryDao.removeAllRecords() }
}