package com.mobilschool.fintrack.di.module

import android.app.Application
import androidx.room.Room
import com.mobilschool.fintrack.data.source.local.db.FinTrackDB
import com.mobilschool.fintrack.data.source.local.entity.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule {

    @Provides
    @Singleton
    fun provideDB(app: Application): FinTrackDB =
            Room.databaseBuilder(app, FinTrackDB::class.java, "fintrack_db").build()

    @Provides
    @Singleton
    fun provideCurrencyDao(db: FinTrackDB) = db.currencyDao()

    @Provides
    @Singleton
    fun provideExchangeDao(db: FinTrackDB) = db.exchangeDao()

    @Provides
    @Singleton
    fun provideWalletDao(db: FinTrackDB) = db.walletDao()
}