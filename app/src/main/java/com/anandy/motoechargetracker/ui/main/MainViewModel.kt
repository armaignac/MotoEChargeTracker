package com.anandy.motoechargetracker.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anandy.motoechargetracker.model.BatteryCharge
import com.anandy.motoechargetracker.model.BatteryChargeRepository
import com.anandy.motoechargetracker.ui.common.ScopedViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.File
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
                "${currentDate.get(Calendar.YEAR)}-${currentDate.get(Calendar.YEAR)}-${currentDate.get(
                    Calendar.YEAR
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


}