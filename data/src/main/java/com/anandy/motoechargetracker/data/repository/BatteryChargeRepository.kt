package com.anandy.motoechargetracker.data.repository

import com.anandy.motoechargetracker.data.source.LocalDataSource
import com.anandy.motoechargetracker.domain.BatteryCharge
import com.anandy.motoechargetracker.domain.MonthlyCharge
import java.util.*

class BatteryChargeRepository(private val localDataSource: LocalDataSource) {

    suspend fun getRecords(startDate: String, endDate: String) =
        localDataSource.getRecords(startDate, endDate)

    suspend fun saveCharge(charge: BatteryCharge) {
        if (charge.id == 0) {
            localDataSource.saveCharge(charge)
        } else {
            localDataSource.updateCharge(charge)
        }
    }

    suspend fun getMonthlyRecords(): List<MonthlyCharge> {
        if (localDataSource.isEmpty()) {
            populateItems().forEach { this.saveCharge(it) }
        }
        return localDataSource.getMonthlyCharges()
    }

    suspend fun remove(charge: BatteryCharge) = localDataSource.remove(charge.id)
    suspend fun getCharge(chargeId: Int): BatteryCharge? = localDataSource.getRecord(chargeId)
    suspend fun removeAllRecords() = localDataSource.removeAllRecords()

    private fun populateItems(): List<BatteryCharge> {
        return listOf(
            BatteryCharge(0, 47, getDate(29, Calendar.JUNE)),
            BatteryCharge(0, 88, getDate(1, Calendar.JULY)),
            BatteryCharge(0, 129, getDate(3, Calendar.JULY)),
            BatteryCharge(0, 157, getDate(4, Calendar.JULY)),
            BatteryCharge(0, 192, getDate(6, Calendar.JULY)),
            BatteryCharge(0, 213, getDate(7, Calendar.JULY)),
            BatteryCharge(0, 234, getDate(8, Calendar.JULY)),
            BatteryCharge(0, 258, getDate(9, Calendar.JULY)),
            BatteryCharge(0, 286, getDate(10, Calendar.JULY)),
            BatteryCharge(0, 320, getDate(11, Calendar.JULY)),
            BatteryCharge(0, 355, getDate(13, Calendar.JULY)),
            BatteryCharge(0, 395, getDate(14, Calendar.JULY)),
            BatteryCharge(0, 411, getDate(15, Calendar.JULY)),
            BatteryCharge(0, 439, getDate(16, Calendar.JULY)),
            BatteryCharge(0, 471, getDate(17, Calendar.JULY)),
            BatteryCharge(0, 506, getDate(18, Calendar.JULY)),


            BatteryCharge(0, 37, getDate(19, Calendar.JULY), true),
            BatteryCharge(0, 51, getDate(20, Calendar.JULY)),
            BatteryCharge(0, 89, getDate(22, Calendar.JULY)),
            BatteryCharge(0, 126, getDate(23, Calendar.JULY)),
            BatteryCharge(0, 201, getDate(25, Calendar.JULY)),
            BatteryCharge(0, 234, getDate(26, Calendar.JULY)),
            BatteryCharge(0, 264, getDate(27, Calendar.JULY)),
            BatteryCharge(0, 301, getDate(29, Calendar.JULY)),
            BatteryCharge(0, 337, getDate(30, Calendar.JULY)),
            BatteryCharge(0, 378, getDate(31, Calendar.JULY)),
            BatteryCharge(0, 413, getDate(1, Calendar.AUGUST)),
            BatteryCharge(0, 450, getDate(2, Calendar.AUGUST)),
            BatteryCharge(0, 497, getDate(3, Calendar.AUGUST)),
            BatteryCharge(0, 542, getDate(5, Calendar.AUGUST)),
            BatteryCharge(0, 565, getDate(6, Calendar.AUGUST)),
            BatteryCharge(0, 581, getDate(7, Calendar.AUGUST)),
            BatteryCharge(0, 620, getDate(8, Calendar.AUGUST)),
            BatteryCharge(0, 647, getDate(10, Calendar.AUGUST)),
            BatteryCharge(0, 693, getDate(11, Calendar.AUGUST)),
            BatteryCharge(0, 740, getDate(12, Calendar.AUGUST)),
            BatteryCharge(0, 779, getDate(14, Calendar.AUGUST)),
            BatteryCharge(0, 811, getDate(15, Calendar.AUGUST)),
            BatteryCharge(0, 852, getDate(16, Calendar.AUGUST)),
            BatteryCharge(0, 899, getDate(18, Calendar.AUGUST)),
            BatteryCharge(0, 946, getDate(20, Calendar.AUGUST)),
            BatteryCharge(0, 981, getDate(21, Calendar.AUGUST)),
            BatteryCharge(0, 1019, getDate(22, Calendar.AUGUST)),
            BatteryCharge(0, 1059, getDate(23, Calendar.AUGUST)),
            BatteryCharge(0, 1104, getDate(25, Calendar.AUGUST)),
            BatteryCharge(0, 1124, getDate(26, Calendar.AUGUST)),
            BatteryCharge(0, 1166, getDate(27, Calendar.AUGUST)),
            BatteryCharge(0, 1200, getDate(28, Calendar.AUGUST)),
            BatteryCharge(0, 1243, getDate(29, Calendar.AUGUST)),
            BatteryCharge(0, 1284, getDate(30, Calendar.AUGUST)),
            BatteryCharge(0, 1309, getDate(31, Calendar.AUGUST)),
            BatteryCharge(0, 1337, getDate(1, Calendar.SEPTEMBER)),
            BatteryCharge(0, 1384, getDate(3, Calendar.SEPTEMBER)),
            BatteryCharge(0, 1407, getDate(4, Calendar.SEPTEMBER)),
            BatteryCharge(0, 1448, getDate(7, Calendar.SEPTEMBER)),
            BatteryCharge(0, 1488, getDate(8, Calendar.SEPTEMBER)),
            BatteryCharge(0, 1523, getDate(10, Calendar.SEPTEMBER)),
            BatteryCharge(0, 1558, getDate(11, Calendar.SEPTEMBER)),
            BatteryCharge(0, 1603, getDate(12, Calendar.SEPTEMBER)),
            BatteryCharge(0, 1630, getDate(14, Calendar.SEPTEMBER)),
            BatteryCharge(0, 1667, getDate(15, Calendar.SEPTEMBER)),
            BatteryCharge(0, 1699, getDate(16, Calendar.SEPTEMBER)),
            BatteryCharge(0, 1736, getDate(17, Calendar.SEPTEMBER)),
            BatteryCharge(0, 1777, getDate(18, Calendar.SEPTEMBER)),
            BatteryCharge(0, 1811, getDate(19, Calendar.SEPTEMBER)),
            BatteryCharge(0, 1851, getDate(21, Calendar.SEPTEMBER)),
            BatteryCharge(0, 1895, getDate(22, Calendar.SEPTEMBER)),
            BatteryCharge(0, 1923, getDate(23, Calendar.SEPTEMBER)),
            BatteryCharge(0, 1964, getDate(24, Calendar.SEPTEMBER)),
            BatteryCharge(0, 1999, getDate(26, Calendar.SEPTEMBER)),
            BatteryCharge(0, 2031, getDate(28, Calendar.SEPTEMBER)),
            BatteryCharge(0, 2047, getDate(29, Calendar.SEPTEMBER)),
            BatteryCharge(0, 2075, getDate(30, Calendar.SEPTEMBER))
        )
    }

    private fun getDate(dayOfMonth: Int, month: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        calendar.set(Calendar.MONTH, month)
        return calendar.time
    }

}