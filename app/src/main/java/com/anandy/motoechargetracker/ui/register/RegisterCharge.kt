package com.anandy.motoechargetracker.ui.register

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.anandy.motoechargetracker.ui.common.DatePickerFragment
import com.anandy.motoechargetracker.R
import com.anandy.motoechargetracker.app
import com.anandy.motoechargetracker.databinding.ActivityRegisterChargeBinding
import com.anandy.motoechargetracker.getViewModel
import com.anandy.motoechargetracker.model.BatteryChargeRepository

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