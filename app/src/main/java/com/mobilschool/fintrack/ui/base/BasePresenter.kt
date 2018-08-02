package com.mobilschool.fintrack.ui.base

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<T: BaseView>: MvpPresenter<T>() {

    protected lateinit var compositeDisposable: CompositeDisposable

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
    
}