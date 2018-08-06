package com.mobilschool.fintrack.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mobilschool.fintrack.data.source.local.entity.PeriodicTransaction
import java.util.*

@Dao
interface PeriodicTransactionDao : BaseDao<PeriodicTransaction>{

    @Query("SELECT * FROM periodic_transactions")
    fun selectPeriodicTransactions(): LiveData<List<PeriodicTransaction>>

    @Query("UPDATE periodic_transactions SET last_execution = :newDate WHERE id = :id")
    fun updatePeriodicTransactionLastExecution(id: Int, newDate: Date)

}