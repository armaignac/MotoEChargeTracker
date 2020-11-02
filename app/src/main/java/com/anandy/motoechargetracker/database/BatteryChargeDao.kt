package com.anandy.motoechargetracker.database

import androidx.room.*
import com.anandy.motoechargetracker.domain.MonthlyCharge
import java.util.*

@Dao
interface BatteryChargeDao {

    @Query(
        "SELECT * FROM BatteryCharge " +
                "WHERE case when (:startDate = '') " +
                "      then 1 = 1 " +
                "      else (date(chargeDate) >= date(:startDate) " +
                "        and date(chargeDate) <= date(:endDate))" +
                "      end " +
                "ORDER BY date(chargeDate) DESC"
    )
    fun getRecords(startDate: String, endDate: String): List<BatteryCharge>

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

    @Query(
        "SELECT max(chargeDate) as monthDate, " +
                "case when (max(kilometers) = min(kilometers)) " +
                "  then max(kilometers) " +
                "  else max(kilometers) - min(kilometers) " +
                "end as totalKilometers, " +
                "count(id) as totalCharges, " +
                "max(kilometers) as lastKilometers " +
                "FROM BatteryCharge " +
                "GROUP BY strftime('%m-%Y', chargeDate) " +
                "ORDER BY strftime('%m-%Y', chargeDate) DESC"
    )
    fun getMonthlyCharges(): List<MonthlyCharge>
}