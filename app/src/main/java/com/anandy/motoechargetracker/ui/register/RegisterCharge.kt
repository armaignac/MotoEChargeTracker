package com.anandy.motoechargetracker.ui.register

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
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
import com.anandy.motoechargetracker.ui.register.RegisterChargeViewModel.UiModel.UpdateUI
import kotlinx.android.synthetic.main.activity_register_charge.*
import kotlinx.coroutines.launch

import java.text.SimpleDateFormat
import java.util.*


class RegisterCharge : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterChargeBinding
    private lateinit var viewModel: RegisterChargeViewModel
    private lateinit var datePicker: DatePickerFragment
    private lateinit var chargeRepository: BatteryChargeRepository
    private var charge: BatteryCharge? = null
    private val dateFormat = "dd/MM/yyyy"
    private val sdf = SimpleDateFormat(dateFormat, Locale.US)

    companion object {
        const val EXTRA_ID = "RegisterChargeActivity:id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterChargeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chargeRepository = BatteryChargeRepository(app)

        datePicker = DatePickerFragment() { selectedDate ->
            binding.textDateCharge.setText(sdf.format(selectedDate))
        }

        datePicker.selectedDate = Calendar.getInstance().time
        binding.textDateCharge.setText(sdf.format(datePicker.selectedDate))
        binding.textDateCharge.setOnClickListener {
            datePicker.show(supportFragmentManager, "datePicker")
        }

        viewModel = getViewModel { RegisterChargeViewModel(chargeRepository) }
        viewModel.model.observe(this, Observer(::updateUi))

        val chargeId = intent.getIntExtra(EXTRA_ID, -1)
        viewModel.onLoadCharge(chargeId)
    }

    private fun updateUi(model: RegisterChargeViewModel.UiModel) {
        when (model) {
            is Navigation -> startActivity<MainActivity>()
            is UpdateUI -> {
                charge = model.charge
                binding.textKilometers.setText(model.charge.kilometers.toString())
                binding.textDateCharge.setText(sdf.format(model.charge.date))
                datePicker.selectedDate = model.charge.date
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.register_charge, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_charge -> {
                if (charge == null) {
                    charge = BatteryCharge(
                        0,
                        binding.textKilometers.text.toString().toInt(),
                        datePicker.selectedDate
                    )
                } else {
                    charge?.kilometers = binding.textKilometers.text.toString().toInt()
                    charge?.date = datePicker.selectedDate
                }
                viewModel.onSave(charge!!)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}