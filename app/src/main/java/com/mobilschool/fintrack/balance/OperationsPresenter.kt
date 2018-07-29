package com.mobilschool.fintrack.CurrentBalance

import com.mobilschool.fincalc.TypeOperation
import java.text.SimpleDateFormat
import java.util.*

class OperationsPresenter : OperationsContract.Presenter {

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