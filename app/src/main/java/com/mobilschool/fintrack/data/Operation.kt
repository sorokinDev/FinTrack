package com.mobilschool.fintrack.data

import com.mobilschool.fincalc.TypeOperation

data class Operation(
        val amount: String,
        val currencyName: String,
        val date: String,
        val category: String,
        val type: TypeOperation
)