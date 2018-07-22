package com.mobilschool.fincalc.entity.currency

class USD(amount: Double = 0.0, name: String = "Dollar") : Currency(amount, name) {


    override fun getConvertor(): CurrencyConvertor {
        return USDConvertor()
    }


    private inner class USDConvertor : CurrencyConvertor {

        val exchangeRates = mapOf("Ruble" to 63.47)

        override fun convert(amount: Double, nameCurrency: String): Currency {

          return  when (nameCurrency) {
                "Ruble" -> RUB(exchangeRates[nameCurrency]!! * amount)
                "Dollar" -> this@USD
                else -> throw NotImplementedError()
            }

        }


    }

}