package com.anandy.motoechargetracker.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.anandy.motoechargetracker.app
import com.anandy.motoechargetracker.ui.register.RegisterCharge
import com.anandy.motoechargetracker.databinding.ActivityMainBinding
import com.anandy.motoechargetracker.getViewModel
import com.anandy.motoechargetracker.model.BatteryChargeRepository
import com.anandy.motoechargetracker.startActivity
import com.anandy.motoechargetracker.ui.main.MainViewModel.UiModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val recordsAdapter =
        BatteryChargeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel {MainViewModel(BatteryChargeRepository(app))}

        binding.batteryChargeRecycler.adapter = recordsAdapter

        viewModel.model.observe(this, Observer(::updateUi))

        binding.fab.setOnClickListener { _ ->
            startActivity<RegisterCharge>()
        }
    }

    private fun updateUi(model: UiModel){
        when (model) {
            is UiModel.Content -> {
                recordsAdapter.items = model.records
            }
        }
    }
}