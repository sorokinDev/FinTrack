package com.mobilschool.fintrack.data.source.local.db

import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.TypeConverters
import com.mobilschool.fintrack.data.source.local.converter.CurrencyPairConverter
import com.mobilschool.fintrack.data.source.local.converter.DateConverter
import com.mobilschool.fintrack.data.source.local.dao.CurrencyDao
import com.mobilschool.fintrack.data.source.local.dao.ExchangeDao
import com.mobilschool.fintrack.data.source.local.dao.WalletDao
import com.mobilschool.fintrack.data.source.local.entity.LocalExchangeRate
import com.mobilschool.fintrack.data.source.local.entity.MoneyCurrency


@Database(entities = [(MoneyCurrency::class), (LocalExchangeRate::class)], version = 1)
@TypeConverters(CurrencyPairConverter::class, DateConverter::class)
abstract class FinTrackDB : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
    abstract fun exchangeDao(): ExchangeDao
    abstract fun walletDao(): WalletDao

}