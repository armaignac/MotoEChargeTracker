package com.anandy.motoechargetracker.ui.main

import android.view.View
import android.view.ViewGroup
import com.anandy.motoechargetracker.R
import com.anandy.motoechargetracker.databinding.BatteryChargeParentBinding
import com.anandy.motoechargetracker.databinding.BatteryChargeRecordBinding
import com.anandy.motoechargetracker.domain.BatteryCharge
import com.anandy.motoechargetracker.domain.MonthlyCharge
import com.anandy.motoechargetracker.inflate
import com.anandy.motoechargetracker.ui.common.AsyncExpandableRecyclerViewAdapter
import com.anandy.motoechargetracker.ui.common.AsyncItemLoad
import com.anandy.motoechargetracker.ui.common.AsyncViewHolder
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class BatteryChargeAdapter(
    itemsLoader: AsyncItemLoad<MonthlyCharge, BatteryCharge>,
    private val clickListener: (ChargeItemAction, BatteryCharge) -> Unit
) :
    AsyncExpandableRecyclerViewAdapter<MonthlyCharge, BatteryCharge>(itemsLoader) {

    override fun createParentViewHolder(parent: ViewGroup): AsyncViewHolder {
        val view = parent.inflate(R.layout.battery_charge_parent)

        return ParentViewHolder(
            view
        )
    }

    override fun bindParentViewHolder(holder: AsyncViewHolder, parentItem: MonthlyCharge) {
        (holder as ParentViewHolder).bind(parentItem)
    }

    override fun createChildViewHolder(parent: ViewGroup): ChildViewHolder {
        val view = parent.inflate(R.layout.battery_charge_record)

        return ChildViewHolder(
            view
        )
    }

    override fun bindChildViewHolder(
        holder: AsyncViewHolder,
        rowIndex: Int,
        itemIndex: Int
    ) {
        val nextHeader = if (items.size > rowIndex + 1) items[rowIndex + 1].parent else null
        val rowItems = items[rowIndex].childs
        val item = rowItems[itemIndex]
        val nextItem = when {
            rowItems.size > itemIndex + 1 -> rowItems[itemIndex + 1]
            nextHeader != null -> BatteryCharge(
                kilometers = nextHeader.lastKilometers,
                date = nextHeader.monthDate
            )
            else -> null
        }

        holder.itemView.setOnClickListener {
            clickListener(ChargeItemAction.EDIT, item)
        }

        (holder as ChildViewHolder).bind(item, nextItem, clickListener)
    }

    class ParentViewHolder(view: View) : AsyncViewHolder(view) {
        private val binding = BatteryChargeParentBinding.bind(view)
        private val format = SimpleDateFormat("MMMM, yyyy", Locale.US)

        fun bind(item: MonthlyCharge) {
            binding.charges.text = "+${item.totalCharges} cargas"
            binding.totalKM.text = "+${item.totalKilometers.toString()} km"
            binding.monthDate.text = format.format(item.monthDate)
        }
    }

    class ChildViewHolder(view: View) : AsyncViewHolder(view) {
        private val binding = BatteryChargeRecordBinding.bind(view)
        private val format = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.US)

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