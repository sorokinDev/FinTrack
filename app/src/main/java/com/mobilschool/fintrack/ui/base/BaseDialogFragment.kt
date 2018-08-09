package com.mobilschool.fintrack.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mobilschool.fintrack.di.factory.ViewModelFactory
import dagger.android.support.DaggerAppCompatDialogFragment
import javax.inject.Inject

abstract class BaseDialogFragment<T: BaseViewModel>: DaggerAppCompatDialogFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = provideViewModel()
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun provideViewModel(): T

    inline fun <reified T : ViewModel> getViewModel(
            viewModelFactory: ViewModelProvider.Factory
    ): T = ViewModelProviders.of(this, viewModelFactory)[T::class.java]
}