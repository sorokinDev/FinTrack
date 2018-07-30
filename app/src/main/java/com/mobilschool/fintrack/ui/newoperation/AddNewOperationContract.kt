package com.mobilschool.fintrack.ui.newoperation

import com.mobilschool.fintrack.ui.BaseView
import com.mobilschool.fintrack.data.Operation

interface AddNewOperationContract {

    interface View : BaseView<Presenter> {
        fun showMessage(isSuccess:Boolean)
    }

    interface Presenter {
        fun addOperation(op:Operation)
    }
}