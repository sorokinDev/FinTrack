package com.mobilschool.fintrack.ui.balance

import com.mobilschool.fintrack.ui.BaseView
import io.reactivex.Single

interface BalanceContract {

    interface View : BaseView<Presenter> {}

    interface Presenter {

        fun getBalance(to: String): Single<Double?>

        fun getBalanceByCategories(): Map<String, Double>
    }
}