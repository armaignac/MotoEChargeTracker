package com.anandy.motoechargetracker.database

import androidx.room.*
import com.anandy.motoechargetracker.model.BatteryCharge

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