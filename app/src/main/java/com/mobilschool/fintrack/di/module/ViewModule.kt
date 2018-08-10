package com.mobilschool.fintrack.di.module

import com.mobilschool.fintrack.ui.home.HomeFragment
import com.mobilschool.fintrack.ui.transaction.add.TransactionAddBottomSheetFragment
import com.mobilschool.fintrack.ui.home.bottom_sheet.WalletsBottomSheetFragment
import com.mobilschool.fintrack.ui.main.MainActivity
import com.mobilschool.fintrack.ui.template.TemplatesPeriodicsFragment
import com.mobilschool.fintrack.ui.template.add.TemplateAddFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun walletsBottomSheetFragment(): WalletsBottomSheetFragment

    @ContributesAndroidInjector
    abstract fun transactionAddBottomSheetFragment(): TransactionAddBottomSheetFragment

    @ContributesAndroidInjector
    abstract fun templatesPeriodicsFragment(): TemplatesPeriodicsFragment

    @ContributesAndroidInjector
    abstract fun templateAddFragment(): TemplateAddFragment

}