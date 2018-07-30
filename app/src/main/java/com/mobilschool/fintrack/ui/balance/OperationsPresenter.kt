package com.mobilschool.fintrack.ui.balance

import com.mobilschool.fincalc.TypeOperation
import com.mobilschool.fintrack.data.Operation
import java.text.SimpleDateFormat
import java.util.*

class OperationsPresenter(var view: OperationsContract.View) : OperationsContract.Presenter {

    init {
        view.presenter = this
    }

    override fun getOperations(): List<Operation> {

        val operations = mutableListOf<Operation>()

        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val currentDate = sdf.format(Date())

        operations.add(Operation("100", "руб", currentDate, "Продукты", TypeOperation.OUTCOME))
        operations.add(Operation("500", "руб", currentDate, "Продукты", TypeOperation.OUTCOME))
        operations.add(Operation("500000", "руб", currentDate, "Продукты", TypeOperation.OUTCOME))

        return operations

    }
}