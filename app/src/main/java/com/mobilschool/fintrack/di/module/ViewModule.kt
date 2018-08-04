package com.mobilschool.fintrack.di.module

import com.mobilschool.fintrack.ui.home.HomeFragment
import com.mobilschool.fintrack.ui.main.MainActivity
import com.mobilschool.fintrack.ui.transaction.add.TransactionAddFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun transactionAddFragment(): TransactionAddFragment

}