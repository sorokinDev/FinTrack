package com.mobilschool.fintrack.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mobilschool.fintrack.data.source.local.entity.LocalExchangeRate
import com.mobilschool.fintrack.util.CurrencyPair

@Dao
interface ExchangeDao: BaseDao<LocalExchangeRate>{

    @Query("SELECT * FROM exchange_rates WHERE currencies = :curr")
    fun getExchangeRate(curr: CurrencyPair): LiveData<List<LocalExchangeRate>>

    @Query("SELECT * FROM exchange_rates WHERE currencies in (:cur)")
    fun getExchangeRates(cur: List<CurrencyPair>): LiveData<List<LocalExchangeRate>>

}