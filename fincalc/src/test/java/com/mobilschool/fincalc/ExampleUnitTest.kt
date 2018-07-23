package com.mobilschool.fincalc

import com.mobilschool.fincalc.entity.currency.RUB
import com.mobilschool.fincalc.entity.currency.USD
import org.junit.Test

import org.junit.Assert.*
import org.hamcrest.CoreMatchers.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class OperationsUnitTest {
    @Test
    fun calc_getBalance_isCorrect() {

        val listRecords = listOf(
                Record(TypeOperation.INCOME, USD(100.0)),
                Record(TypeOperation.INCOME, RUB(20.0)),
                Record(TypeOperation.OUTCOME, USD(50.0))
        )


        val balance = Operations.getBalance(listRecords, "RUB")

        assertThat(balance, `is`(3193.5) )
    }
}
