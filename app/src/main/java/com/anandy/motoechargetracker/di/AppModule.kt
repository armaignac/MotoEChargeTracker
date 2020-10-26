package com.anandy.motoechargetracker.di

import android.app.Application
import androidx.room.Room
import com.anandy.motoechargetracker.data.source.LocalDataSource
import com.anandy.motoechargetracker.database.BatteryChargeDatabase
import com.anandy.motoechargetracker.database.RoomDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
        app,
        BatteryChargeDatabase::class.java, "charge-db"
    ).build()

    @Provides
    fun localDataSourceProvider(db: BatteryChargeDatabase): LocalDataSource = RoomDataSource(db)
}