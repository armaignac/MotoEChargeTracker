package com.anandy.batterychargetracker.database

import androidx.room.*
import com.anandy.batterychargetracker.model.BatteryCharge

@Dao
interface BatteryChargeDao {

    @Query("SELECT * FROM BatteryCharge ORDER BY date DESC")
    fun getRecords(): List<BatteryCharge>

    @Query("SELECT COUNT(id) FROM BatteryCharge")
    fun getRecordsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCharge(charge: BatteryCharge)

    @Update
    fun updateCharge(charge: BatteryCharge)
}