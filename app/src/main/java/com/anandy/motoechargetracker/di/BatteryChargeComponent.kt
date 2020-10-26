package com.anandy.motoechargetracker.di

import android.app.Application
import com.anandy.motoechargetracker.ui.main.MainFragmentComponent
import com.anandy.motoechargetracker.ui.main.MainFragmentModule
import com.anandy.motoechargetracker.ui.register.RegisterChargeFragmentComponent
import com.anandy.motoechargetracker.ui.register.RegisterChargeFragmentModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface BatteryChargeComponent {

    fun plus(module: MainFragmentModule): MainFragmentComponent
    fun plus(module: RegisterChargeFragmentModule): RegisterChargeFragmentComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): BatteryChargeComponent
    }
}