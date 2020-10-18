package com.anandy.motoechargetracker.ui.register

import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("chargeDate")
fun EditText.setChargeDate(date: Date?) {
    date?.apply {
        val dateFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(dateFormat, Locale.US)
        setText(sdf.format(this))
    }
}