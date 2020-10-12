package com.anandy.motoechargetracker.ui.main

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.AlertDialog
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.anandy.motoechargetracker.*
import com.anandy.motoechargetracker.ui.register.RegisterCharge
import com.anandy.motoechargetracker.databinding.ActivityMainBinding
import com.anandy.motoechargetracker.model.BatteryCharge
import com.anandy.motoechargetracker.model.BatteryChargeRepository
import com.anandy.motoechargetracker.ui.main.MainViewModel.UiModel
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val recordsAdapter = BatteryChargeAdapter(::recordsClickListener)
    private val coarsePermissionRequester = PermissionRequester(this, WRITE_EXTERNAL_STORAGE)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel { MainViewModel(BatteryChargeRepository(app)) }

        binding.batteryChargeRecycler.adapter = recordsAdapter

        viewModel.model.observe(this, Observer(::updateUi))

        binding.fab.setOnClickListener { _ ->
            startActivity<RegisterCharge>()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.export_records -> {
                //MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL).
                val exportFolder =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                        ?.absolutePath
                coarsePermissionRequester.request { viewModel.onExportRecords(exportFolder!!) }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateUi(model: UiModel) {
        when (model) {
            is UiModel.Content -> {
                recordsAdapter.items = model.records
            }
            is UiModel.Notify -> toast(model.msg)
        }
    }

    private fun recordsClickListener(
        action: BatteryChargeAdapter.ChargeItemAction,
        charge: BatteryCharge
    ) {

        when (action) {
            BatteryChargeAdapter.ChargeItemAction.REMOVE -> {
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Delete")
                dialogBuilder.setMessage("Do you want to remove the record?")
                dialogBuilder.setPositiveButton("Yes") { _, _ ->
                    viewModel.onClickedItemAction(action, charge)
                }
                dialogBuilder.setNegativeButton("No") { _, _ -> }
                dialogBuilder.show()
            }

            BatteryChargeAdapter.ChargeItemAction.EDIT -> {
                Log.d("MotoEChargeTraker", "Selected charge $charge")
                startActivity<RegisterCharge>(RegisterCharge.EXTRA_ID to charge.id)
            }
        }

    }
}

