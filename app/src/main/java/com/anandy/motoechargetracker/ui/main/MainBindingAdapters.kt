package com.anandy.motoechargetracker.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anandy.motoechargetracker.model.BatteryCharge

@BindingAdapter("items")
fun RecyclerView.setItems(items: List<BatteryCharge>?) {
    (adapter as? BatteryChargeAdapter)?.let {
        it.items = items ?: emptyList()
    }
}