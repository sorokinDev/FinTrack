package com.mobilschool.fintrack.ui.balance

import com.arellomobile.mvp.InjectViewState
import com.mobilschool.fincalc.Operations
import com.mobilschool.fintrack.FinTrackerApplication
import com.mobilschool.fintrack.data.source.remote.api.ExchangeRateApi
import com.mobilschool.fintrack.ui.base.BasePresenter
import com.mobilschool.fintrack.util.Utils
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class BalancePresenter @Inject constructor(): BasePresenter<BalanceView>() {

    fun getBalance() {

    }

    fun getBalanceByCategories() {


    }

    fun getSumWithoutIncomeOutcome() {

    }



}

