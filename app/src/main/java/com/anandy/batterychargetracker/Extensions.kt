package com.anandy.batterychargetracker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.anandy.batterychargetracker.model.BatteryCharge

import java.util.*

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun RecyclerView.ViewHolder.toast(message: String) = itemView.context.toast(message)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View = LayoutInflater
    .from(context)
    .inflate(layoutRes, this, false)

inline fun <reified T : Activity> Context.startActivity(vararg pairs: Pair<String, Any?>){
    Intent(this, T :: class.java)
        .apply {
            putExtras(bundleOf(*pairs))
        }
        .also { startActivity(it) }
}

fun populateItems() : List<BatteryCharge>{
    return listOf(
        BatteryCharge(37, getDate(19, Calendar.JULY)),
        BatteryCharge(51, getDate(20, Calendar.JULY)),
        BatteryCharge(89, getDate(22, Calendar.JULY)),
        BatteryCharge(126, getDate(23, Calendar.JULY)),
        BatteryCharge(201, getDate(25, Calendar.JULY)),
        BatteryCharge(234, getDate(26, Calendar.JULY)),
        BatteryCharge(264, getDate(27, Calendar.JULY)),
        BatteryCharge(301, getDate(29, Calendar.JULY)),
        BatteryCharge(337, getDate(30, Calendar.JULY)),
        BatteryCharge(378, getDate(31, Calendar.JULY)),
        BatteryCharge(413, getDate(1, Calendar.AUGUST)),
        BatteryCharge(450, getDate(2, Calendar.AUGUST)),
        BatteryCharge(497, getDate(3, Calendar.AUGUST)),
        BatteryCharge(542, getDate(5, Calendar.AUGUST)),
        BatteryCharge(565, getDate(6, Calendar.AUGUST)),
        BatteryCharge(581, getDate(7, Calendar.AUGUST)),
        BatteryCharge(620, getDate(8, Calendar.AUGUST)),
        BatteryCharge(647, getDate(10, Calendar.AUGUST)),
        BatteryCharge(693, getDate(11, Calendar.AUGUST)),
        BatteryCharge(740, getDate(12, Calendar.AUGUST)),
        BatteryCharge(779, getDate(14, Calendar.AUGUST)),
        BatteryCharge(811, getDate(15, Calendar.AUGUST)),
        BatteryCharge(852, getDate(16, Calendar.AUGUST)),
        BatteryCharge(899, getDate(18, Calendar.AUGUST)),
        BatteryCharge(946, getDate(20, Calendar.AUGUST)),
        BatteryCharge(981, getDate(21, Calendar.AUGUST)),
        BatteryCharge(1019, getDate(22, Calendar.AUGUST)),
        BatteryCharge(1059, getDate(23, Calendar.AUGUST)),
        BatteryCharge(1104, getDate(25, Calendar.AUGUST)),
        BatteryCharge(1124, getDate(26, Calendar.AUGUST)),
        BatteryCharge(1166, getDate(27, Calendar.AUGUST)),
        BatteryCharge(1200, getDate(28, Calendar.AUGUST)),
        BatteryCharge(1243, getDate(29, Calendar.AUGUST)),
        BatteryCharge(1284, getDate(30, Calendar.AUGUST)),
        BatteryCharge(1309, getDate(31, Calendar.AUGUST)),
        BatteryCharge(1337, getDate(1, Calendar.SEPTEMBER)),
        BatteryCharge(1384, getDate(3, Calendar.SEPTEMBER)),
        BatteryCharge(1407, getDate(4, Calendar.SEPTEMBER)),
        BatteryCharge(1448, getDate(7, Calendar.SEPTEMBER)),
        BatteryCharge(1488, getDate(8, Calendar.SEPTEMBER)),
        BatteryCharge(1523, getDate(10, Calendar.SEPTEMBER)),
        BatteryCharge(1558, getDate(11, Calendar.SEPTEMBER)),
        BatteryCharge(1603, getDate(12, Calendar.SEPTEMBER)),
        BatteryCharge(1630, getDate(14, Calendar.SEPTEMBER)),
        BatteryCharge(1667, getDate(15, Calendar.SEPTEMBER)),
        BatteryCharge(1699, getDate(16, Calendar.SEPTEMBER)),
        BatteryCharge(1736, getDate(17, Calendar.SEPTEMBER)),
        BatteryCharge(1777, getDate(18, Calendar.SEPTEMBER)),
        BatteryCharge(1811, getDate(19, Calendar.SEPTEMBER)),
        BatteryCharge(1851, getDate(21, Calendar.SEPTEMBER))
    ).reversed()
}

private fun getDate(dayOfMonth: Int, month: Int):Date{
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
    calendar.set(Calendar.MONTH, month)
    return calendar.time
}



