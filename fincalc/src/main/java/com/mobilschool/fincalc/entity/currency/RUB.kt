package com.mobilschool.fincalc.entity.currency

class RUB(amount: Double = 0.0, name: String = "Ruble") : Currency(amount, name) {
    override fun getConvertor(): CurrencyConvertor {
        return RUBConvertor()
    }

    private inner class RUBConvertor : CurrencyConvertor {

        val exchangeRates = mapOf("Dollar" to 0.016)

        override fun convert(amount: Double, nameCurrency: String): Currency {

            return when (nameCurrency) {
                "Dollar" -> USD(exchangeRates[nameCurrency]!! * amount)
                "Ruble" -> this@RUB
                else -> throw NotImplementedError()
            }

        }

    }
}