package com.anandy.motoechargetracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anandy.motoechargetracker.database.Converters

import java.util.*
import kotlin.properties.Delegates

fun Fragment.toast(message: String) =
    Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View = LayoutInflater
    .from(context)
    .inflate(layoutRes, this, false)

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
inline fun <reified T : ViewModel> Fragment.getViewModel(crossinline factory: () -> T): T {

    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProvider(this, vmFactory).get()
}

val Context.app: MotoEChargeTrackerApp
    get() = applicationContext as MotoEChargeTrackerApp

val Fragment.app: MotoEChargeTrackerApp
    get() = ((activity?.app)
        ?: IllegalStateException("Fragment needs to be attach to the activity to access the App instance"))
            as MotoEChargeTrackerApp

fun Date.toChargeDate(): String = Converters.dateFormat.format(this)

fun Date.startDate(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this

    calendar.apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return calendar.time
}

fun Date.endDate(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this

    calendar.apply {
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }
    return calendar.time
}


