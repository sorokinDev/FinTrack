package com.mobilschool.fincalc.entity.currency

interface CurrencyConvertor {
    fun convert(amount: Double, nameCurrency: String): Currency
}