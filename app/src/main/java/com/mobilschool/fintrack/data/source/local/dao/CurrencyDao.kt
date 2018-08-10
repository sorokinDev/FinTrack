package com.mobilschool.fintrack.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mobilschool.fintrack.data.source.local.entity.Currency

@Dao
interface CurrencyDao: BaseDao<Currency> {

    @Query("SELECT * FROM currencies")
    fun getAllCurrencies(): LiveData<List<Currency>>

    @Query("SELECT * FROM currencies WHERE is_favorite = 1")
    fun getFavoriteCurrencies(): LiveData<List<Currency>>

}