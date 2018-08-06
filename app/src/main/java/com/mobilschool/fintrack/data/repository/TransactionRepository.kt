package com.mobilschool.fintrack.data.repository

import androidx.lifecycle.LiveData
import com.mobilschool.fintrack.data.source.local.dao.CategoryDao
import com.mobilschool.fintrack.data.source.local.dao.TransactionDao
import com.mobilschool.fintrack.data.source.local.entity.MoneyTransaction
import com.mobilschool.fintrack.data.source.local.entity.MoneyTransactionWithCategory
import com.mobilschool.fintrack.data.source.local.entity.TransactionCategory
import com.mobilschool.fintrack.data.source.local.entity.TransactionType
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class TransactionRepository @Inject constructor(val transactionDao: TransactionDao,
                                                val categoryDao: CategoryDao) {

    fun getLastNTransactionsForWallet(n: Int, walletId: Int): LiveData<List<MoneyTransactionWithCategory>> {
        return transactionDao.selectLastNTransactionsForWallet(n, walletId)
    }

    fun insertOrUpdateTransaction(transaction: MoneyTransaction){
        launch {
            transactionDao.insertOrUpdate(transaction)
        }

    }

    fun getCategoriesByType(type: TransactionType): LiveData<List<TransactionCategory>>{
        return categoryDao.getCategoriesByType(type)
    }


}