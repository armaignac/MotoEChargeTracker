package com.anandy.motoechargetracker.ui.main

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anandy.motoechargetracker.domain.MonthlyCharge

@BindingAdapter("items")
fun RecyclerView.setItems(items: List<MonthlyCharge>?) {
    Log.d("MotoE", "Binding Adapter Items size ${items}")
    (adapter as? BatteryChargeAdapter)?.setParentRecords(items ?: emptyList())
}