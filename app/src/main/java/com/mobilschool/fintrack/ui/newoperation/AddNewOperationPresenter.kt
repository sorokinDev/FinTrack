package com.mobilschool.fintrack.ui.newoperation

import com.mobilschool.fintrack.data.Operation

class AddNewOperationPresenter(val view: AddNewOperationContract.View) : AddNewOperationContract.Presenter {

    init {
        view.presenter = this
    }

    override fun addOperation(op: Operation) {
        var resulOperation = true
        view.showMessage(resulOperation)
    }
}