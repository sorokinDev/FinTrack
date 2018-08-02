package com.mobilschool.fintrack.di.module

import com.mobilschool.fintrack.ui.balance.BalanceFragment
import com.mobilschool.fintrack.ui.main.MainActivity
import com.mobilschool.fintrack.ui.transactions.TransactionsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun balanceFragment(): BalanceFragment

    @ContributesAndroidInjector
    abstract fun transactionsFragment(): TransactionsFragment

}