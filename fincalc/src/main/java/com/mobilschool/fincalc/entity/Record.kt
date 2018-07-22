package com.mobilschool.fincalc

import com.mobilschool.fincalc.entity.currency.Currency

enum class TypeOperation{
    INCOME, OUTCOME
}

data class Record(
        val typeOperation:TypeOperation,
        val currency: Currency
)