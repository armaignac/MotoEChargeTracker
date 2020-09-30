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

    }

    fun onSave(kilometers: Int, date: Date){
        val charge = BatteryCharge(0, kilometers, date)
        launch {
            chargeRepository.saveCharge(charge)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val chargeRepository: BatteryChargeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MainViewModel(chargeRepository) as T
}