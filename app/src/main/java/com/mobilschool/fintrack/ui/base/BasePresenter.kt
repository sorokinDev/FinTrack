package com.mobilschool.fintrack.ui.base

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView

open class BasePresenter<T: BaseView>: MvpPresenter<T>() {


    override fun onDestroy() {
        super.onDestroy()
    }
    
}