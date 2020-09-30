package com.anandy.motoechargetracker.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anandy.motoechargetracker.model.BatteryCharge
import com.anandy.motoechargetracker.model.BatteryChargeRepository
import com.anandy.motoechargetracker.ui.common.ScopedViewModel
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