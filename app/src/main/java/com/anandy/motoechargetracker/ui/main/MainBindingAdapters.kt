package com.anandy.motoechargetracker.ui.main

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anandy.motoechargetracker.model.BatteryCharge

@BindingAdapter("items")
fun RecyclerView.setItems(items: List<BatteryCharge>?) {
    Log.d("MotoE", "Binding Adapter Items size ${items}")
    (adapter as? BatteryChargeAdapter)?.let {
        it.items = items ?: emptyList()
    }
}