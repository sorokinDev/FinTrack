package com.mobilschool.fintrack.ui.main

import com.arellomobile.mvp.InjectViewState
import com.mobilschool.fintrack.ui.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(): BasePresenter<MainView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showMsg("Hello")
    }

}