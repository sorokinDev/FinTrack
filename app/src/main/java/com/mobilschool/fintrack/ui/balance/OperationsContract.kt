package com.mobilschool.fintrack.ui.balance

import com.mobilschool.fintrack.ui.BaseView
import com.mobilschool.fintrack.data.Operation

interface OperationsContract {

    interface View : BaseView<Presenter> {}

    interface Presenter {
        fun getOperations():List<Operation>
    }

}