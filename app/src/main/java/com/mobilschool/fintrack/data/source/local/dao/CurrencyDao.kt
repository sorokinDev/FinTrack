package com.mobilschool.fintrack.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mobilschool.fintrack.data.source.local.entity.MoneyCurrency

@Dao
interface CurrencyDao: BaseDao<MoneyCurrency> {

    @Query("SELECT * FROM money_currencies")
    fun getAllCurrencies(): LiveData<List<MoneyCurrency>>

    @Query("SELECT * FROM money_currencies WHERE is_favorite = 1")
    fun getFavoriteCurrencies(): LiveData<List<MoneyCurrency>>

}