package com.anandy.motoechargetracker.ui.register

import com.anandy.motoechargetracker.app
import com.anandy.motoechargetracker.data.repository.BatteryChargeRepository
import com.anandy.motoechargetracker.database.RoomDataSource
import com.anandy.motoechargetracker.usecases.*
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class RegisterChargeFragmentModule {

    @Provides
    fun registerChargeViewModelProvider(
        saveCharge: SaveCharge,
        findCharge: FindCharge
    ) = RegisterChargeViewModel(
        saveCharge, findCharge
    )

    @Provides
    fun saveChargeProvider(batteryChargeRepository: BatteryChargeRepository) =
        SaveCharge(batteryChargeRepository)

    @Provides
    fun findChargeProvider(batteryChargeRepository: BatteryChargeRepository) =
        FindCharge(batteryChargeRepository)
}

@Subcomponent(modules = [(RegisterChargeFragmentModule::class)])
interface RegisterChargeFragmentComponent {
    val registerChargeViewModel: RegisterChargeViewModel
}