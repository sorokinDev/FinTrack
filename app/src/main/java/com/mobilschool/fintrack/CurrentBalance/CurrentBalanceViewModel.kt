package com.mobilschool.fintrack.CurrentBalance

import androidx.lifecycle.ViewModel
import com.mobilschool.fincalc.Operations
import com.mobilschool.fincalc.Record
import com.mobilschool.fincalc.TypeOperation
import com.mobilschool.fincalc.entity.currency.RUB
import com.mobilschool.fincalc.entity.currency.USD
import com.mobilschool.fintrack.Repository.Repository

class CurrentBalanceViewModel : ViewModel() {

    private lateinit var repository: Repository

    val mockListOperations = listOf(
            Record(TypeOperation.INCOME, USD(100.0)),
            Record(TypeOperation.INCOME, RUB(20.0)),
            Record(TypeOperation.OUTCOME, USD(50.0))
    )

    fun getBalance(nameCurrency: String): Double {
        return if (nameCurrency == "USD") Operations.getBalance(mockListOperations,"USD")
        else Operations.getBalance(mockListOperations,"RUB")
    }


}