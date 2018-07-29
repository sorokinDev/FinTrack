package com.mobilschool.fintrack.CurrentBalance

import com.mobilschool.fintrack.BaseView

interface BalanceContract {

    interface View : BaseView<Presenter> {

    }


    interface Presenter {

        fun getBalance(to:String): Double

        fun getBalanceByCategories(): Map<String, Double>
    }
}