package com.anandy.motoechargetracker.ui.register

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.anandy.motoechargetracker.ui.common.DatePickerFragment
import com.anandy.motoechargetracker.R
import com.anandy.motoechargetracker.app
import com.anandy.motoechargetracker.databinding.ActivityRegisterChargeBinding
import com.anandy.motoechargetracker.getViewModel
import com.anandy.motoechargetracker.model.BatteryCharge
import com.anandy.motoechargetracker.model.BatteryChargeRepository
import com.anandy.motoechargetracker.startActivity
import com.anandy.motoechargetracker.ui.main.MainActivity
import com.anandy.motoechargetracker.ui.register.RegisterChargeViewModel.UiModel.Navigation

import java.text.SimpleDateFormat
import java.util.*


class RegisterCharge : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterChargeBinding
    private lateinit var viewModel: RegisterChargeViewModel
    private lateinit var datePicker: DatePickerFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterChargeBinding.inflate(layoutInflater)
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
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: RegisterChargeViewModel.UiModel) {
        when (model) {
            is Navigation -> startActivity<MainActivity>()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.register_charge, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_charge -> {
                val charge = BatteryCharge(
                    0,
                    binding.textKilometers.text.toString().toInt(),
                    datePicker.selectedDate
                )
                viewModel.onSave(charge)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}