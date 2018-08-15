package com.mobilschool.fintrack.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mobilschool.fintrack.data.source.local.entity.ExchangeRate
import com.mobilschool.fintrack.util.CurrencyPair

@Dao
interface ExchangeDao: BaseDao<ExchangeRate>{

    @Query("SELECT * FROM exchange_rates WHERE id = :curr")
    fun getExchangeRate(curr: String): LiveData<List<ExchangeRate>>

    @Query("SELECT * FROM exchange_rates WHERE id in (:cur)")
    fun getExchangeRates(cur: List<String>): LiveData<List<ExchangeRate>>

}