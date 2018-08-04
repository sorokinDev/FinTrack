package com.mobilschool.fintrack.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.mobilschool.fintrack.data.source.local.entity.MoneyCurrency
import io.reactivex.Single

@Dao
interface CurrencyDao: BaseDao<MoneyCurrency> {

    @Query("SELECT * FROM money_currencies")
    fun getAllCurrencies(): Single<List<MoneyCurrency>>

    @Query("SELECT * FROM money_currencies WHERE is_favorite = 1")
    fun getFavoriteCurrencies(): Single<List<MoneyCurrency>>

}