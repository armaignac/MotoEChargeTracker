package com.anandy.batterychargetracker

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.anandy.batterychargetracker.databinding.ActivityMainBinding
import com.anandy.batterychargetracker.databinding.ContentMainBinding
import com.anandy.batterychargetracker.model.BatteryChargeAdapter

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
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}