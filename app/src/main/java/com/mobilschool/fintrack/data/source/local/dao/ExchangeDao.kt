package com.mobilschool.fintrack.data.source.local.dao

import androidx.room.*
import com.mobilschool.fintrack.data.source.local.entity.LocalExchangeRate
import com.mobilschool.fintrack.util.CurrencyPair
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ExchangeDao: BaseDao<LocalExchangeRate>{

    @Query("SELECT * FROM exchange_rates WHERE currencies = :curr")
    fun getExchangeRate(curr: CurrencyPair): Flowable<List<LocalExchangeRate>>

}