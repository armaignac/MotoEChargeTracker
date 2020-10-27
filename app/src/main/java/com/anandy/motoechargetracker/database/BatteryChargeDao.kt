package com.anandy.motoechargetracker.database

import android.database.Cursor
import androidx.room.*
import java.util.*

@Dao
interface BatteryChargeDao {

    @Query("SELECT * FROM BatteryCharge ORDER BY date(chargeDate) DESC")
    fun getRecords(): List<BatteryCharge>

    @Query("SELECT COUNT(id) FROM BatteryCharge")
    fun getRecordsCount(): Int

    @Query("SELECT * FROM BatteryCharge WHERE id = :id")
    fun getCharge(id: Int): BatteryCharge?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCharge(charge: BatteryCharge)

    @Update
    fun updateCharge(charge: BatteryCharge)

    @Query("DELETE FROM BatteryCharge WHERE id = :chargeId")
    fun remove(chargeId: Int)

    @Query("DELETE FROM BatteryCharge")
    fun removeAllRecords()

    @Query("SELECT max(resetId) from BatteryCharge")
    fun getCurrentResetId(): Int
}