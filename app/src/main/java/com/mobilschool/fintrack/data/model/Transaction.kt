package com.mobilschool.fintrack.data.model

import com.mobilschool.fincalc.TypeOperation

data class Transaction(
        val amount: String,
        val currencyName: String,
        val date: String,
        val category: String,
        val type: TypeOperation
)