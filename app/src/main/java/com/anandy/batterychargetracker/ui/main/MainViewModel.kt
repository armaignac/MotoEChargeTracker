package com.anandy.batterychargetracker.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anandy.batterychargetracker.model.BatteryCharge
import com.anandy.batterychargetracker.model.BatteryChargeRepository
import com.anandy.batterychargetracker.ui.common.ScopedViewModel
import kotlinx.coroutines.launch

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
        class Content (val records: List<BatteryCharge>) : UiModel()
    }

    private fun refresh(){
        launch {
            _model.value = UiModel.Content(chargeRepository.getRecords())
        }
    }
}