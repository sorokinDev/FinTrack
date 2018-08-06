package com.mobilschool.fintrack.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mobilschool.fintrack.data.source.local.entity.MoneyTransaction

@Dao
interface TransactionDao: BaseDao<MoneyTransaction> {

    @Query("SELECT * FROM money_transactions ORDER BY date DESC LIMIT (:n)")
    fun selectLastNTransactions(n: Int): LiveData<List<MoneyTransaction>>
}