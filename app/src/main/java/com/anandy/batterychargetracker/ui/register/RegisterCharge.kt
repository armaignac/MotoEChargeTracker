package com.anandy.batterychargetracker.ui.register

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.anandy.batterychargetracker.ui.common.DatePickerFragment
import com.anandy.batterychargetracker.R
import com.anandy.batterychargetracker.app
import com.anandy.batterychargetracker.databinding.ActivityRegisterChargeBinding
import com.anandy.batterychargetracker.getViewModel
import com.anandy.batterychargetracker.model.BatteryChargeRepository

import java.text.SimpleDateFormat
import java.util.*


class RegisterCharge : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterChargeBinding
    private lateinit var viewModel: RegisterChargeViewModel
    private lateinit var datePicker: DatePickerFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRegisterChargeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dateFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(dateFormat, Locale.US)
        datePicker = DatePickerFragment() { selectedDate ->
            binding.textDateCharge.setText(sdf.format(selectedDate))
        }

        binding.textDateCharge.setOnClickListener {
            datePicker.show(supportFragmentManager, "datePicker")
        }

        viewModel = getViewModel { RegisterChargeViewModel(BatteryChargeRepository(app)) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.register_charge, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.saveCharge) viewModel.onSave(binding.textKilometers.text.toString().toInt(), datePicker.selectedDate)
        return super.onOptionsItemSelected(item)
    }
}