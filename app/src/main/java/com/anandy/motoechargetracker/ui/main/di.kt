package com.anandy.motoechargetracker.ui.main

import com.anandy.motoechargetracker.app
import com.anandy.motoechargetracker.data.repository.BatteryChargeRepository
import com.anandy.motoechargetracker.database.RoomDataSource
import com.anandy.motoechargetracker.usecases.GetRegisteredCharges
import com.anandy.motoechargetracker.usecases.ImportCharges
import com.anandy.motoechargetracker.usecases.RemoveCharge
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MainFragmentModule {

    @Provides
    fun mainViewModelProvider(
        getRegisteredCharges: GetRegisteredCharges,
        removeCharge: RemoveCharge,
        importCharges: ImportCharges
    ) = MainViewModel(
        getRegisteredCharges, removeCharge, importCharges
    )

    @Provides
    fun getRegisteredChargesProvider(batteryChargeRepository: BatteryChargeRepository) =
        GetRegisteredCharges(batteryChargeRepository)

    @Provides
    fun removeChargeProvider(batteryChargeRepository: BatteryChargeRepository) =
        RemoveCharge(batteryChargeRepository)

    @Provides
    fun importChargesProvider(batteryChargeRepository: BatteryChargeRepository) =
        ImportCharges(batteryChargeRepository)
}

@Subcomponent(modules = [(MainFragmentModule::class)])
interface MainFragmentComponent {
    val mainViewModel: MainViewModel
}