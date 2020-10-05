package com.anandy.motoechargetracker.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anandy.motoechargetracker.model.BatteryCharge
import com.anandy.motoechargetracker.model.BatteryChargeRepository
import com.anandy.motoechargetracker.ui.common.ScopedViewModel
import com.anandy.motoechargetracker.ui.main.MainViewModel
import kotlinx.coroutines.launch
import java.util.*

class RegisterChargeViewModel(private val chargeRepository: BatteryChargeRepository) : ScopedViewModel()  {
    private val _model = MutableLiveData<UiModel>()

    val model: LiveData<UiModel>
        get() {
            return _model
        }

    init {
        initScope()
    }


    sealed class UiModel {
        class Navigation : UiModel()
        class UpdateUI(val charge: BatteryCharge) : UiModel()
    }

    fun onSave(charge: BatteryCharge) {
        launch {
            chargeRepository.saveCharge(charge)
            _model.value = UiModel.Navigation()
        }
    }

    fun onLoadCharge(chargeId: Int) {
        launch {
            val charge = chargeRepository.getCharge(chargeId)
            if (charge != null) _model.postValue(UiModel.UpdateUI(charge))
        }
    }
}