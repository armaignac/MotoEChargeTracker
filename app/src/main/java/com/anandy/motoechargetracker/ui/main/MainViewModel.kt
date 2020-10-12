package com.anandy.motoechargetracker.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anandy.motoechargetracker.model.BatteryCharge
import com.anandy.motoechargetracker.model.BatteryChargeRepository
import com.anandy.motoechargetracker.ui.common.ScopedViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.net.URI
import java.util.*

class MainViewModel(private val chargeRepository: BatteryChargeRepository) : ScopedViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if(_model.value == null) refresh()
            return _model
        }

    init {
        initScope()
    }

    sealed class UiModel {
        class Content(val records: List<BatteryCharge>) : UiModel()
        class Notify(val msg: String) : UiModel()
        class Progress() : UiModel()
    }

    private fun refresh() {
        launch {
            _model.value = UiModel.Content(chargeRepository.getRecords())
        }
    }

    fun onClickedItemAction(action: BatteryChargeAdapter.ChargeItemAction, item: BatteryCharge) {
        launch {
            when (action) {
                BatteryChargeAdapter.ChargeItemAction.REMOVE -> removeItem(item)
            }
        }
    }

    fun onExportRecords(exportForlder: String) {
        launch {
            val records = chargeRepository.getRecords()
            val jsonString = Gson().toJson(records)
            val currentDate = Calendar.getInstance()
            val dateName =
                "${currentDate.get(Calendar.YEAR)}-${currentDate.get(Calendar.MONTH)}-${currentDate.get(
                    Calendar.DAY_OF_MONTH
                )}"
            val timeName =
                "${currentDate.get(Calendar.HOUR_OF_DAY)}-${currentDate.get(Calendar.MINUTE)}"
            val dateTimeName = "$dateName-$timeName"
            val exportFileName = "$exportForlder/MotoE-$dateTimeName.json"
            val file = File(exportFileName)
            file.writeText(jsonString)
            _model.value = UiModel.Notify("Registros exportados en ${file.absolutePath}")
        }
    }

    private suspend fun removeItem(charge: BatteryCharge) {
        chargeRepository.remove(charge)
        refresh()
    }

    fun onImportRecords(selectedFileContent: String) {
        val chargeListType = object : TypeToken<List<BatteryCharge>>() {}.type
        val fileRecords = Gson().fromJson<List<BatteryCharge>>(selectedFileContent, chargeListType)
        launch {
            _model.value = UiModel.Progress()
            chargeRepository.removeAllRecords()
            fileRecords.forEach {
                it.id = 0
                chargeRepository.saveCharge(it)
            }
            refresh()
        }
    }
}