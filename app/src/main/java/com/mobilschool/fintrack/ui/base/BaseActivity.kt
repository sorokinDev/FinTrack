package com.mobilschool.fintrack.ui.base

import android.annotation.SuppressLint
import dagger.android.support.DaggerAppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mobilschool.fintrack.di.factory.ViewModelFactory
import javax.inject.Inject


@SuppressLint("Registered")
abstract class BaseActivity<T: ViewModel>: DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel()
    }

    lateinit var viewModel: T

    inline fun <reified T : ViewModel> getViewModel(
            viewModelFactory: ViewModelProvider.Factory
    ): T = ViewModelProviders.of(this, viewModelFactory)[T::class.java]


    abstract fun provideViewModel(): T
}