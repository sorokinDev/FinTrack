package com.mobilschool.fintrack.di.module

import android.app.Application
import com.mobilschool.fintrack.data.source.local.entity.*
import dagger.Module
import dagger.Provides
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import javax.inject.Singleton

@Module
class DBModule {

    @Provides
    @Singleton
    fun provideBoxStore(app: Application): BoxStore = MyObjectBox.builder().androidContext(app).build()

    @Provides
    @Singleton
    fun provideWalletBox(boxStore: BoxStore): Box<Wallet> = boxStore.boxFor()

    @Provides
    @Singleton
    fun provideTransactionBox(boxStore: BoxStore): Box<MoneyTransaction> = boxStore.boxFor()

    @Provides
    @Singleton
    fun provideTransactionCategoryBox(boxStore: BoxStore): Box<TransactionCategory> = boxStore.boxFor()

    @Provides
    @Singleton
    fun provideCurrencyBox(boxStore: BoxStore): Box<MoneyCurrency> = boxStore.boxFor()
}