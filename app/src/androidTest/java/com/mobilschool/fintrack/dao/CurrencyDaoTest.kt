package com.mobilschool.fintrack.dao

import com.mobilschool.fintrack.LiveDataTestUtil
import com.mobilschool.fintrack.data.source.local.dao.CurrencyDao
import com.mobilschool.fintrack.data.source.local.entity.Currency
import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyDaoTest: BaseDaoTest() {

    lateinit var currencyDao: CurrencyDao

    override fun initDao() {
        currencyDao = db.currencyDao()
    }

    @Test
    @Throws(Exception::class)
    fun getFavoriteCurrencies_Correct() {

        val currencies = listOf(
                Currency("FAV1", "Р", true),
                Currency("NOT_FAV1", "Р", false),
                Currency("FAV2", "Р", true),
                Currency("NOT_FAV2", "Р", false)
        )

        currencyDao.insertOrUpdateAll(currencies)

        val favoriteCurrencies = LiveDataTestUtil.getValue(currencyDao.getFavoriteCurrencies())

        assertEquals(favoriteCurrencies.size, 2)
    }

}