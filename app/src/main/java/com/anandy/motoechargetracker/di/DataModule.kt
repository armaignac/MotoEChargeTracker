package com.anandy.motoechargetracker.di

import com.anandy.motoechargetracker.data.repository.BatteryChargeRepository
import com.anandy.motoechargetracker.data.source.LocalDataSource
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun batteryChargeRepositoryProvider(localDataSource: LocalDataSource) =
        BatteryChargeRepository(localDataSource)
}