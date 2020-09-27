package com.anandy.batterychargetracker.ui.register

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.anandy.batterychargetracker.ui.common.DatePickerFragment
import com.anandy.batterychargetracker.R
import com.anandy.batterychargetracker.databinding.ActivityRegisterChargeBinding

import java.text.SimpleDateFormat
import java.util.*


class RegisterCharge : AppCompatActivity() {
//    private val binding: ActivityRegisterChargeBinding
//    private var charge: Charge

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRegisterChargeBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        charge = Charge()
        binding.textDateCharge.setOnClickListener {
            val datePicker =
                DatePickerFragment() { selectedDate ->
                    val dateFormat = "dd/MM/yyyy"
                    val sdf = SimpleDateFormat(dateFormat, Locale.US)
                    binding.textDateCharge.setText(sdf.format(selectedDate))
//                charge.date = selectedDate
                }
            datePicker.show(supportFragmentManager, "datePicker")
         }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.register_charge, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //charge.kilometers =  binding.textKilometers.text.toString().toInt()
        return super.onOptionsItemSelected(item)
    }


}