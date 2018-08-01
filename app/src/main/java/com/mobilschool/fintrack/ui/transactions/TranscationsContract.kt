package com.mobilschool.fintrack.ui.transactions

import com.mobilschool.fintrack.ui.BaseView
import com.mobilschool.fintrack.data.model.Transaction

interface TranscationsContract {

    interface View : BaseView<Presenter> {}

    interface Presenter {

        fun getOperations(): List<Transaction>

        fun attachView(view: View)

        fun detachView()
    }

}