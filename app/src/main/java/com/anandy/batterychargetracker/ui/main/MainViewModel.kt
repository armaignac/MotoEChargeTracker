package com.anandy.batterychargetracker.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anandy.batterychargetracker.model.BatteryCharge
import com.anandy.batterychargetracker.model.BatteryChargeRepository

class MainViewModel(private val chargeRepository: BatteryChargeRepository) : ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if(_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        class Content (val records: List<BatteryCharge>) : UiModel()
    }

    private fun refresh(){
        _model.value = UiModel.Content(chargeRepository.getRecords())
    }
}