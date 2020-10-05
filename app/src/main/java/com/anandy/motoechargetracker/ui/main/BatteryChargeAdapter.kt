package com.anandy.motoechargetracker.ui.main

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.anandy.motoechargetracker.R
import com.anandy.motoechargetracker.basicDiffUtil
import com.anandy.motoechargetracker.databinding.BatteryChargeRecordBinding
import com.anandy.motoechargetracker.inflate
import com.anandy.motoechargetracker.model.BatteryCharge
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit


class BatteryChargeAdapter(private val clickListener: (ChargeItemAction, BatteryCharge) -> Unit) :
    RecyclerView.Adapter<BatteryChargeAdapter.ViewHolder>() {

    var items: List<BatteryCharge> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.battery_charge_record)

        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val nextItem = if (items.size > position + 1) items[position + 1] else null
        holder.itemView.setOnClickListener {
            clickListener(ChargeItemAction.EDIT, item)
        }
        holder.bind(item, nextItem, clickListener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = BatteryChargeRecordBinding.bind(view)
        private val format = SimpleDateFormat("EEEE, dd MMMM yyyy")

        fun bind(
            item: BatteryCharge,
            nextItem: BatteryCharge?,
            clickListener: (ChargeItemAction, BatteryCharge) -> Unit
        ) {
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
            binding.deleteRecord.setOnClickListener {
                clickListener(ChargeItemAction.REMOVE, item)
            }
        }
    }

    enum class ChargeItemAction {
        REMOVE,
        EDIT
    }
}