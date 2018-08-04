package com.mobilschool.fintrack

import com.mobilschool.fintrack.data.source.remote.api.ExchangeRateApi
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        ExchangeRateApi.ratesService
                .getRate("RUB","USD")
                .map { it.rates["USD"]?.times(30000) }
                .subscribe { currensyRates->
                    print(currensyRates)
                }


    }
}
