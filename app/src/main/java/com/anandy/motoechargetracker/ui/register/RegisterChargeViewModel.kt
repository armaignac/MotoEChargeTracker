package com.anandy.motoechargetracker.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anandy.motoechargetracker.domain.BatteryCharge
import com.anandy.motoechargetracker.ui.common.Event
import com.anandy.motoechargetracker.ui.common.ScopedViewModel
import com.anandy.motoechargetracker.usecases.FindCharge
import com.anandy.motoechargetracker.usecases.SaveCharge
import kotlinx.coroutines.launch
import java.util.*

class RegisterChargeViewModel(
    private val saveCharge: SaveCharge,
    private val findCharge: FindCharge
) :
    ScopedViewModel() {
    private val _kilometers = MutableLiveData<String>()
    val kilometers: LiveData<String>
        get() {
            return _kilometers
        }

    private val _chargeDate = MutableLiveData<Date>()
    val chargeDate: LiveData<Date>
        get() {
            return _chargeDate
        }

    private val _navigateToMain = MutableLiveData<Event<Int>>()
    val navigateToMain: LiveData<Event<Int>> get() = _navigateToMain

    private val _charge = MutableLiveData<BatteryCharge?>()
    val charge: LiveData<BatteryCharge?>
        get() {
            return _charge
        }

    init {
        initScope()
    }

    fun onSave(charge: BatteryCharge) {
        launch {
            saveCharge.invoke(charge)
            _navigateToMain.value = Event(charge.id)
        }
    }

    fun onLoadCharge(chargeId: Int) {
        launch {
            val charge = findCharge.invoke(chargeId)
            if (charge != null) {
                _kilometers.value = charge.kilometers.toString()
                _chargeDate.value = charge.date
            } else {
                _chargeDate.value = Calendar.getInstance().time
            }
            _charge.value = charge
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    fun onDateSelected(selectedDate: Date) {
        _chargeDate.value = selectedDate
    }
}