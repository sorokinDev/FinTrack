package com.mobilschool.fintrack.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mobilschool.fintrack.data.source.local.dao.CategoryDao
import com.mobilschool.fintrack.data.source.local.dao.PeriodicTransactionDao
import com.mobilschool.fintrack.data.source.local.dao.TransactionDao
import com.mobilschool.fintrack.data.source.local.entity.*
import kotlinx.coroutines.experimental.launch
import java.util.*
import javax.inject.Inject

class TransactionRepository @Inject constructor(val transactionDao: TransactionDao,
                                                val categoryDao: CategoryDao,
                                                val periodicTransactionDao: PeriodicTransactionDao) {

    fun getLastNTransactionsForWallet(n: Int, walletId: Int): LiveData<List<MoneyTransactionWithCategory>> {
        return transactionDao.selectLastNTransactionsForWallet(n, walletId)
    }

    fun insertOrUpdateTransaction(transaction: MoneyTransaction){
        launch {
            transactionDao.insertOrUpdate(transaction)
        }

    }

    fun insertAllTransactions(transactions: List<MoneyTransaction>){
        launch {
            transactionDao.insertOrUpdateAll(transactions)
        }

    }

    fun getCategoriesByType(type: TransactionType): LiveData<List<TransactionCategory>>{
        return categoryDao.getCategoriesByType(type)
    }

    fun getPeriodicTransactions(): LiveData<List<PeriodicTransaction>>{
        return periodicTransactionDao.selectPeriodicTransactions()
    }

    fun getUnexecutedPeriodicTransactions(): LiveData<List<PeriodicTransaction>> {
        return Transformations.map(getPeriodicTransactions()){ trans ->
            trans.filter {
                (it.lastExecution.time + it.frequency) < Date().time
            }
        }
    }

    fun updatePeriodicTransactionLastExecution(id: Int, newDate: Date) {
        launch {
            periodicTransactionDao.updatePeriodicTransactionLastExecution(id, newDate)
        }
    }

    fun insertPeriodicTransaction(trans: PeriodicTransaction) {
        launch {
            periodicTransactionDao.insertOrUpdate(trans)
        }
    }


}