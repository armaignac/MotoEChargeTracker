package com.anandy.batterychargetracker.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.anandy.batterychargetracker.ui.register.RegisterCharge
import com.anandy.batterychargetracker.databinding.ActivityMainBinding
import com.anandy.batterychargetracker.databinding.ContentMainBinding
import com.anandy.batterychargetracker.getViewModel
import com.anandy.batterychargetracker.model.BatteryChargeRepository
import com.anandy.batterychargetracker.startActivity
import com.anandy.batterychargetracker.ui.main.MainViewModel.UiModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val recordsAdapter =
        BatteryChargeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        viewModel = getViewModel {MainViewModel(BatteryChargeRepository())}

        val mainContent: ContentMainBinding = binding.content
        mainContent.batteryChargeRecycler.adapter = recordsAdapter

        viewModel.model.observe(this, Observer(::updateUi))

        binding.fab.setOnClickListener { view ->
            startActivity<RegisterCharge>()
        }
    }

    private fun updateUi(model: UiModel){
        when (model) {
            is UiModel.Content -> recordsAdapter.items = model.records
        }
    }
}