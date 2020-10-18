package com.anandy.motoechargetracker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anandy.motoechargetracker.model.BatteryCharge

import java.util.*
import kotlin.properties.Delegates

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun RecyclerView.ViewHolder.toast(message: String) = itemView.context.toast(message)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View = LayoutInflater
    .from(context)
    .inflate(layoutRes, this, false)

inline fun <reified T : Activity> Context.startActivity(vararg pairs: Pair<String, Any?>) {
    Intent(this, T::class.java)
        .apply {
            putExtras(bundleOf(*pairs))
        }
        .also { startActivity(it) }
}

fun <T : ViewDataBinding> ViewGroup.bindingInflate(
    @LayoutRes layoutRes: Int,
    attachToRoot: Boolean = true
): T =
    DataBindingUtil.inflate(LayoutInflater.from(context), layoutRes, this, attachToRoot)

inline fun <VH : RecyclerView.ViewHolder, T> RecyclerView.Adapter<VH>.basicDiffUtil(
    initialValue: List<T>,
    crossinline areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    crossinline areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new }
) =
    Delegates.observable(initialValue) { _, old, new ->
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                areItemsTheSame(old[oldItemPosition], new[newItemPosition])

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                areContentsTheSame(old[oldItemPosition], new[newItemPosition])

            override fun getOldListSize(): Int = old.size

            override fun getNewListSize(): Int = new.size
        }).dispatchUpdatesTo(this@basicDiffUtil)
    }

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> FragmentActivity.getViewModel(crossinline factory: () -> T): T {

    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }
    return ViewModelProvider(this, vmFactory).get()
}

val Context.app: BatteryChargeApp
    get() = applicationContext as BatteryChargeApp

fun populateItems() : List<BatteryCharge>{
    return listOf(
        BatteryCharge(0, 37, getDate(19, Calendar.JULY)),
        BatteryCharge(0,51, getDate(20, Calendar.JULY)),
        BatteryCharge(0,89, getDate(22, Calendar.JULY)),
        BatteryCharge(0,126, getDate(23, Calendar.JULY)),
        BatteryCharge(0,201, getDate(25, Calendar.JULY)),
        BatteryCharge(0,234, getDate(26, Calendar.JULY)),
        BatteryCharge(0,264, getDate(27, Calendar.JULY)),
        BatteryCharge(0,301, getDate(29, Calendar.JULY)),
        BatteryCharge(0,337, getDate(30, Calendar.JULY)),
        BatteryCharge(0,378, getDate(31, Calendar.JULY)),
        BatteryCharge(0,413, getDate(1, Calendar.AUGUST)),
        BatteryCharge(0,450, getDate(2, Calendar.AUGUST)),
        BatteryCharge(0,497, getDate(3, Calendar.AUGUST)),
        BatteryCharge(0,542, getDate(5, Calendar.AUGUST)),
        BatteryCharge(0,565, getDate(6, Calendar.AUGUST)),
        BatteryCharge(0,581, getDate(7, Calendar.AUGUST)),
        BatteryCharge(0,620, getDate(8, Calendar.AUGUST)),
        BatteryCharge(0,647, getDate(10, Calendar.AUGUST)),
        BatteryCharge(0,693, getDate(11, Calendar.AUGUST)),
        BatteryCharge(0,740, getDate(12, Calendar.AUGUST)),
        BatteryCharge(0,779, getDate(14, Calendar.AUGUST)),
        BatteryCharge(0,811, getDate(15, Calendar.AUGUST)),
        BatteryCharge(0,852, getDate(16, Calendar.AUGUST)),
        BatteryCharge(0,899, getDate(18, Calendar.AUGUST)),
        BatteryCharge(0,946, getDate(20, Calendar.AUGUST)),
        BatteryCharge(0,981, getDate(21, Calendar.AUGUST)),
        BatteryCharge(0,1019, getDate(22, Calendar.AUGUST)),
        BatteryCharge(0,1059, getDate(23, Calendar.AUGUST)),
        BatteryCharge(0,1104, getDate(25, Calendar.AUGUST)),
        BatteryCharge(0,1124, getDate(26, Calendar.AUGUST)),
        BatteryCharge(0,1166, getDate(27, Calendar.AUGUST)),
        BatteryCharge(0,1200, getDate(28, Calendar.AUGUST)),
        BatteryCharge(0,1243, getDate(29, Calendar.AUGUST)),
        BatteryCharge(0,1284, getDate(30, Calendar.AUGUST)),
        BatteryCharge(0,1309, getDate(31, Calendar.AUGUST)),
        BatteryCharge(0,1337, getDate(1, Calendar.SEPTEMBER)),
        BatteryCharge(0,1384, getDate(3, Calendar.SEPTEMBER)),
        BatteryCharge(0,1407, getDate(4, Calendar.SEPTEMBER)),
        BatteryCharge(0,1448, getDate(7, Calendar.SEPTEMBER)),
        BatteryCharge(0,1488, getDate(8, Calendar.SEPTEMBER)),
        BatteryCharge(0,1523, getDate(10, Calendar.SEPTEMBER)),
        BatteryCharge(0,1558, getDate(11, Calendar.SEPTEMBER)),
        BatteryCharge(0,1603, getDate(12, Calendar.SEPTEMBER)),
        BatteryCharge(0,1630, getDate(14, Calendar.SEPTEMBER)),
        BatteryCharge(0,1667, getDate(15, Calendar.SEPTEMBER)),
        BatteryCharge(0,1699, getDate(16, Calendar.SEPTEMBER)),
        BatteryCharge(0,1736, getDate(17, Calendar.SEPTEMBER)),
        BatteryCharge(0,1777, getDate(18, Calendar.SEPTEMBER)),
        BatteryCharge(0,1811, getDate(19, Calendar.SEPTEMBER)),
        BatteryCharge(0,1851, getDate(21, Calendar.SEPTEMBER)),
        BatteryCharge(0,1895, getDate(22, Calendar.SEPTEMBER)),
        BatteryCharge(0,1923, getDate(23, Calendar.SEPTEMBER)),
        BatteryCharge(0,1964, getDate(24, Calendar.SEPTEMBER)),
        BatteryCharge(0,1999, getDate(26, Calendar.SEPTEMBER)),
        BatteryCharge(0,2031, getDate(28, Calendar.SEPTEMBER)),
        BatteryCharge(0,2047, getDate(29, Calendar.SEPTEMBER)),
        BatteryCharge(0,2075, getDate(30, Calendar.SEPTEMBER))
    )
}

private fun getDate(dayOfMonth: Int, month: Int):Date{
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
    calendar.set(Calendar.MONTH, month)
    return calendar.time
}



