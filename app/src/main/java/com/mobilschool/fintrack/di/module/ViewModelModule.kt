package com.mobilschool.fintrack.di.module

import androidx.lifecycle.ViewModel
import com.mobilschool.fintrack.di.factory.ViewModelKey
import com.mobilschool.fintrack.ui.home.HomeViewModel
import com.mobilschool.fintrack.ui.main.MainViewModel
import com.mobilschool.fintrack.ui.transaction.add.TransactionAddViewModel
import com.mobilschool.fintrack.ui.template.TemplatesPeriodicsViewModel
import com.mobilschool.fintrack.ui.template.add.TemplateAddViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionAddViewModel::class)
    abstract fun bindTransactionAddViewModel(viewModel: TransactionAddViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TemplatesPeriodicsViewModel::class)
    abstract fun bindTemplatesPeriodicsViewModel(viewModel: TemplatesPeriodicsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TemplateAddViewModel::class)
    abstract fun bindTemplateAddViewModel(viewModel: TemplateAddViewModel): ViewModel

}