package com.anandy.batterychargetracker.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anandy.batterychargetracker.model.BatteryCharge
import com.anandy.batterychargetracker.model.BatteryChargeRepository
import com.anandy.batterychargetracker.ui.main.MainViewModel
import java.util.*

class RegisterChargeViewModel(private val chargeRepository: BatteryChargeRepository) : ViewModel()  {
    private val _model = MutableLiveData<UiModel>()

    val model: LiveData<UiModel>
        get() {
            return _model
        }


    sealed class UiModel {

    }

    fun onSave(kilometers: Int, date: Date){
        val charge = BatteryCharge(kilometers, date)
        chargeRepository.saveCharge(charge)
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val chargeRepository: BatteryChargeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MainViewModel(chargeRepository) as T
}