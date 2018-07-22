package com.mobilschool.fincalc.entity.currency


abstract class Currency(val amount: Double, val name: String) {

    fun convertTo(name: String): Currency {

        val currencyConvertor = getConvertor()
        return currencyConvertor.convert(amount, name)

    }

    protected abstract fun getConvertor(): CurrencyConvertor

}