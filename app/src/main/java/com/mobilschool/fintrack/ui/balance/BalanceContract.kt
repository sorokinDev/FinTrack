package com.mobilschool.fintrack.ui.balance

import com.mobilschool.fintrack.ui.BaseView
import io.reactivex.Observable
import io.reactivex.Single

interface BalanceContract {

    interface View : BaseView<Presenter> {}

    interface Presenter {

        fun getBalance(to: String): Single<Double?>

        fun getBalanceByCategories(): Observable<Pair<String, Double>>

        fun getSumWithoutIncomeOutcome():Single<Double>

        fun attachView(view: View)

        fun detachView()
    }
}