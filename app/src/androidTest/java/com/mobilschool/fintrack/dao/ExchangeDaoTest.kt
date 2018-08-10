package com.mobilschool.fintrack.dao

import com.mobilschool.fintrack.LiveDataTestUtil
import com.mobilschool.fintrack.data.source.local.dao.ExchangeDao
import com.mobilschool.fintrack.data.source.local.entity.ExchangeRate
import com.mobilschool.fintrack.data.source.local.entity.TransactionType
import org.junit.Assert
import org.junit.Test
import java.util.*

class ExchangeDaoTest: BaseDaoTest() {
    lateinit var exchangeDao: ExchangeDao

    override fun initDao() {
        exchangeDao = db.exchangeDao()
        exchangeDao.insertOrUpdateAll(listOf(
                ExchangeRate("USD_RUB", 60.0, Date().time),
                ExchangeRate("EUR_RUB", 80.0, Date().time),
                ExchangeRate("RUB_USD", 1 / 60.0, Date().time),
                ExchangeRate("RUB_EUR", 1 / 60.0, Date().time)
        ))
    }

    @Test
    fun getExchangeRate_Correct() {

        val oneRate = LiveDataTestUtil.getValue(exchangeDao.getExchangeRate("RUB_USD"))
        val someRates = LiveDataTestUtil.getValue(exchangeDao.getExchangeRates(listOf("RUB_USD", "RUB_EUR", "UNKNOWN_CURRENCY")))

        Assert.assertEquals(oneRate.size, 1)
        Assert.assertEquals(someRates.size, 2)
    }


}