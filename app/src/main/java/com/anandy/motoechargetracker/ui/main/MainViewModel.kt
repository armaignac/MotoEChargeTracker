package com.anandy.motoechargetracker.ui.main

import android.app.AlertDialog
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anandy.motoechargetracker.model.BatteryCharge
import com.anandy.motoechargetracker.model.BatteryChargeRepository
import com.anandy.motoechargetracker.ui.common.Event
import com.anandy.motoechargetracker.ui.common.ScopedViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class MainViewModel(private val chargeRepository: BatteryChargeRepository) : ScopedViewModel() {

    private val _items = MutableLiveData<List<BatteryCharge>>()
    val items: LiveData<List<BatteryCharge>> get() = _items

    private val _navigateToRegister = MutableLiveData<Event<Int>>()
    val navigateToRegister: LiveData<Event<Int>> get() = _navigateToRegister

    private val _removeCharge = MutableLiveData<Event<BatteryCharge>>()
    val removeCharge: LiveData<Event<BatteryCharge>> get() = _removeCharge

    private val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>> get() = _toastMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        initScope()
        refresh()
    }

    private fun refresh() {
        launch {
            _items.value = chargeRepository.getRecords()
        }
    }

    fun onClickedItemAction(action: BatteryChargeAdapter.ChargeItemAction, item: BatteryCharge) {
        when (action) {
            BatteryChargeAdapter.ChargeItemAction.REMOVE -> _removeCharge.value = Event(item)
            BatteryChargeAdapter.ChargeItemAction.EDIT -> {
                Log.d("MotoEChargeTraker", "Selected charge $item")
                _navigateToRegister.value = Event(item.id)
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
            _toastMessage.value = Event("Registros exportados en ${file.absolutePath}")
        }
    }

    fun onRemoveItem(charge: BatteryCharge) {
        launch {
            chargeRepository.remove(charge)
            refresh()
        }
    }

    fun onImportRecords(selectedFileContent: String) {
        val chargeListType = object : TypeToken<List<BatteryCharge>>() {}.type
        val fileRecords = Gson().fromJson<List<BatteryCharge>>(selectedFileContent, chargeListType)
        launch {
            _loading.value = true
            chargeRepository.removeAllRecords()
            fileRecords.forEach {
                it.id = 0
                chargeRepository.saveCharge(it)
            }
            refresh()
            _loading.value = false
        }
    }
}