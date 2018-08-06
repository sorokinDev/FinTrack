package com.mobilschool.fintrack.data.repository

import androidx.lifecycle.LiveData
import com.mobilschool.fintrack.data.source.local.dao.TransactionDao
import com.mobilschool.fintrack.data.source.local.entity.MoneyTransaction
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class TransactionRepository @Inject constructor(val transactionDao: TransactionDao) {

    fun getLastNTransactions(n: Int): LiveData<List<MoneyTransaction>> {
        return transactionDao.selectLastNTransactions(n)
    }

    fun insertOrUpdateTransaction(transaction: MoneyTransaction){
        launch {
            transactionDao.insertOrUpdate(transaction)
        }

    }


}