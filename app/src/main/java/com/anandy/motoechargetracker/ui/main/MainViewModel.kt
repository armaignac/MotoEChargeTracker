package com.anandy.motoechargetracker.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anandy.motoechargetracker.domain.BatteryCharge
import com.anandy.motoechargetracker.domain.MonthlyCharge
import com.anandy.motoechargetracker.endDate
import com.anandy.motoechargetracker.toChargeDate
import com.anandy.motoechargetracker.startDate
import com.anandy.motoechargetracker.ui.common.AsyncItemLoad
import com.anandy.motoechargetracker.ui.common.Event
import com.anandy.motoechargetracker.ui.common.ScopedViewModel
import com.anandy.motoechargetracker.usecases.GetMonthlyCharges
import com.anandy.motoechargetracker.usecases.GetRegisteredCharges
import com.anandy.motoechargetracker.usecases.ImportCharges
import com.anandy.motoechargetracker.usecases.RemoveCharge
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class MainViewModel(
    private val monthlyCharges: GetMonthlyCharges,
    private val registeredCharges: GetRegisteredCharges,
    private val removeCharge: RemoveCharge,
    private val importCharges: ImportCharges
) : ScopedViewModel(),
    AsyncItemLoad<MonthlyCharge, BatteryCharge> {

    private val _items = MutableLiveData<List<MonthlyCharge>>()
    val items: LiveData<List<MonthlyCharge>> get() = _items

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
            _items.value = monthlyCharges.invoke()
        }
    }

    fun onExportRecords(exportFolder: String) {
        launch {
            val records = registeredCharges.invoke("", "")
            val jsonString = Gson().toJson(records)
            val currentDate = Calendar.getInstance()
            val dateName =
                "${currentDate.get(Calendar.YEAR)}-${currentDate.get(Calendar.MONTH) + 1}-${
                currentDate.get(
                    Calendar.DAY_OF_MONTH
                )
                }"
            val timeName =
                "${currentDate.get(Calendar.HOUR_OF_DAY)}-${currentDate.get(Calendar.MINUTE)}"
            val dateTimeName = "$dateName-$timeName"
            val exportFileName = "$exportFolder/MotoE-$dateTimeName.json"
            val file = File(exportFileName)
            file.writeText(jsonString)
            _toastMessage.value = Event("Registros exportados en ${file.absolutePath}")
        }
    }

    fun onRemoveItem(charge: BatteryCharge) {
        launch {
            removeCharge.invoke(charge)
        }
    }

    fun onImportRecords(selectedFileContent: String) {
        val chargeListType = object : TypeToken<List<BatteryCharge>>() {}.type
        val fileRecords = Gson().fromJson<List<BatteryCharge>>(selectedFileContent, chargeListType)
        launch {
            _loading.value = true
            importCharges.invoke(fileRecords)
            refresh()
            _loading.value = false
        }
    }

    override suspend fun onLoadChildRecords(parent: MonthlyCharge): List<BatteryCharge> {

        val calendar = Calendar.getInstance()
        calendar.time = parent.monthDate
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val startDate = calendar.time.startDate().toChargeDate()

        calendar.set(
            Calendar.DAY_OF_MONTH,
            calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        )
        val endDate = calendar.time.endDate().toChargeDate()
        return registeredCharges.invoke(startDate, endDate)
    }
}