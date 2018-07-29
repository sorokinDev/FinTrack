package com.mobilschool.fintrack.CurrentBalance

import com.mobilschool.fintrack.BaseView

class OperationsContract {

    interface View : BaseView<Presenter> {

    }


    interface Presenter {
        fun getOperations():List<Operation>
    }

}