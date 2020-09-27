package com.anandy.batterychargetracker.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anandy.batterychargetracker.ui.register.RegisterCharge
import com.anandy.batterychargetracker.databinding.ActivityMainBinding
import com.anandy.batterychargetracker.databinding.ContentMainBinding
import com.anandy.batterychargetracker.model.BatteryChargeAdapter
import com.anandy.batterychargetracker.startActivity

class MainActivity : AppCompatActivity() {

    private val recordsAdapter = BatteryChargeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val mainContent: ContentMainBinding = binding.content
        mainContent.batteryChargeRecycler.adapter = recordsAdapter

        binding.fab.setOnClickListener { view ->
            startActivity<RegisterCharge>()
        }
    }
}