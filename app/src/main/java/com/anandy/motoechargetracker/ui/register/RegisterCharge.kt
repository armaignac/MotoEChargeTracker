package com.anandy.motoechargetracker.ui.register

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.anandy.motoechargetracker.*
import com.anandy.motoechargetracker.ui.common.DatePickerFragment
import com.anandy.motoechargetracker.databinding.ActivityRegisterChargeBinding
import com.anandy.motoechargetracker.model.BatteryCharge
import com.anandy.motoechargetracker.model.BatteryChargeRepository
import com.anandy.motoechargetracker.ui.main.MainActivity
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

    companion object {
        const val EXTRA_ID = "RegisterChargeActivity:id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        chargeRepository = BatteryChargeRepository(app)

        viewModel = getViewModel { RegisterChargeViewModel(chargeRepository) }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_charge)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        datePicker = DatePickerFragment() { selectedDate ->
            viewModel.onDateSelected(selectedDate)
        }

        datePicker.selectedDate = Calendar.getInstance().time

        binding.textDateCharge.setOnClickListener {
            datePicker.show(supportFragmentManager, "datePicker")
        }

        viewModel.chargeDate.observe(this, Observer { date -> datePicker.selectedDate = date })
        viewModel.charge.observe(this, Observer { modelCharge -> charge = modelCharge })
        viewModel.navigateToMain.observe(this, Observer { _ -> startActivity<MainActivity>() })

        val chargeId = intent.getIntExtra(EXTRA_ID, -1)
        viewModel.onLoadCharge(chargeId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.register_charge, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_charge -> {
                if (binding.textKilometers.text.isNotEmpty() && binding.textKilometers.text.toString().toInt() > 0) {
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
                } else {
                    toast("Los kilometros deben ser mayor que 0")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}