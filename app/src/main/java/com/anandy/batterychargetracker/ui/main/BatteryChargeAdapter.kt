package com.anandy.batterychargetracker.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anandy.batterychargetracker.R
import com.anandy.batterychargetracker.databinding.BatteryChargeRecordBinding
import com.anandy.batterychargetracker.inflate
import com.anandy.batterychargetracker.model.BatteryCharge
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit


class BatteryChargeAdapter(var items: List<BatteryCharge> = emptyList()): RecyclerView.Adapter<BatteryChargeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.battery_charge_record)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val nextItem = if(items.size > position + 1) items[position+1] else null
        holder.bind(item, nextItem)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = BatteryChargeRecordBinding.bind(view)
        private val format = SimpleDateFormat("EEEE, dd MMMM yyyy")

        fun bind(item: BatteryCharge, nextItem: BatteryCharge?) {
            binding.textChargeKM.text = item.kilometers.toString()
            binding.textChargeDate.text = format.format(item.date)
            binding.textKMDifference.text = ""
            binding.textDateDifference.text = ""
            nextItem?.apply {
                val diffInMillisec: Long = item.date.time - date.time
                val days = TimeUnit.MILLISECONDS.toDays(diffInMillisec)
                binding.textKMDifference.text = "+${item.kilometers - kilometers} km"
                binding.textDateDifference.text = "+ $days día(s)"
            }
        }
    }
}