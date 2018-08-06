package com.mobilschool.fintrack.di.module

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mobilschool.fintrack.data.source.local.db.FinTrackDB
import com.mobilschool.fintrack.data.source.local.entity.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule {

    @Provides
    @Singleton
    fun provideDB(app: Application): FinTrackDB = FinTrackDB.getInstance(app)

    @Provides
    @Singleton
    fun provideCurrencyDao(db: FinTrackDB) = db.currencyDao()

    @Provides
    @Singleton
    fun provideExchangeDao(db: FinTrackDB) = db.exchangeDao()

    @Provides
    @Singleton
    fun provideWalletDao(db: FinTrackDB) = db.walletDao()

    @Provides
    @Singleton
    fun provideTransactionDao(db: FinTrackDB) = db.transactionDao()

    @Provides
    @Singleton
    fun provideCategoryDao(db: FinTrackDB) = db.categoryDao()
}