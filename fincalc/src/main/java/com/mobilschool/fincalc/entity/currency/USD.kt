package com.mobilschool.fincalc.entity.currency

class USD(amount: Double = 0.0, name: String = "USD") : Currency(amount, name) {


    override fun getConvertor(): CurrencyConvertor {
        return USDConvertor()
    }


    private inner class USDConvertor : CurrencyConvertor {

        val exchangeRates = mapOf("RUB" to 63.47)

        override fun convert(amount: Double, nameCurrency: String): Currency {

          return  when (nameCurrency) {
                "RUB" -> RUB(exchangeRates[nameCurrency]!! * amount)
                "USD" -> this@USD
                else -> throw NotImplementedError()
            }

        }


    }

}