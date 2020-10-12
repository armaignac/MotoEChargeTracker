package com.anandy.motoechargetracker.database

import androidx.room.*
import com.anandy.motoechargetracker.model.BatteryCharge

@Dao
interface BatteryChargeDao {

    @Query("SELECT * FROM BatteryCharge ORDER BY date DESC")
    fun getRecords(): List<BatteryCharge>

    @Query("SELECT COUNT(id) FROM BatteryCharge")
    fun getRecordsCount(): Int

    @Query("SELECT * FROM BatteryCharge WHERE id = :id")
    fun getCharge(id: Int): BatteryCharge?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCharge(charge: BatteryCharge)

    @Update
    fun updateCharge(charge: BatteryCharge)

    @Query("DELETE FROM BatteryCharge WHERE id = :id")
    fun remove(id: Int): Int

    @Query("DELETE FROM BatteryCharge")
    fun removeAllRecords()


}