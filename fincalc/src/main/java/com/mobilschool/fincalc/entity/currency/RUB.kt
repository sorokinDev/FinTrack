package com.mobilschool.fincalc.entity.currency

class RUB(amount: Double = 0.0, name: String = "RUB") : Currency(amount, name) {
    override fun getConvertor(): CurrencyConvertor {
        return RUBConvertor()
    }

    private inner class RUBConvertor : CurrencyConvertor {

        val exchangeRates = mapOf("USD" to 0.016)

        override fun convert(amount: Double, nameCurrency: String): Currency {

            return when (nameCurrency) {
                "USD" -> USD(exchangeRates[nameCurrency]!! * amount)
                "RUB" -> this@RUB
                else -> throw NotImplementedError()
            }

        }

    }
}