package com.anandy.motoechargetracker.ui.main

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.anandy.motoechargetracker.*
import com.anandy.motoechargetracker.databinding.ActivityMainBinding
import com.anandy.motoechargetracker.model.BatteryCharge
import com.anandy.motoechargetracker.model.BatteryChargeRepository
import com.anandy.motoechargetracker.ui.main.MainViewModel.UiModel
import com.anandy.motoechargetracker.ui.register.RegisterCharge
import java.io.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val recordsAdapter = BatteryChargeAdapter(::recordsClickListener)
    private val coarsePermissionRequester = PermissionRequester(this)
    private val chooserRquestCode = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel { MainViewModel(BatteryChargeRepository(app)) }

        binding.batteryChargeRecycler.adapter = recordsAdapter

        viewModel.model.observe(this, Observer(::updateUi))

        binding.fab.setOnClickListener { _ ->
            startActivity<RegisterCharge>()
        }

        supportActionBar?.setTitle(R.string.title_activity_main)
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
                coarsePermissionRequester.request(WRITE_EXTERNAL_STORAGE) {
                    viewModel.onExportRecords(
                        exportFolder!!
                    )
                }
            }
            R.id.import_records -> {
                coarsePermissionRequester.request(READ_EXTERNAL_STORAGE) {
                    val intent = Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT)

                    startActivityForResult(
                        Intent.createChooser(intent, "Seleccionar un archivo"),
                        chooserRquestCode
                    )
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == chooserRquestCode && resultCode == RESULT_OK) {
            val uri = data?.data!!
            val br: BufferedReader
            try {
                br = BufferedReader(InputStreamReader(contentResolver.openInputStream(uri)))
                val content = br.readText()
                br.close()
                viewModel.onImportRecords(content)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun updateUi(model: UiModel) {
        binding.progressIndicator.visibility = View.GONE
        when (model) {
            is UiModel.Content -> {
                recordsAdapter.items = model.records
            }
            is UiModel.Notify -> toast(model.msg)
            is UiModel.Progress -> binding.progressIndicator.visibility = View.VISIBLE
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

