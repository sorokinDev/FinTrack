package com.mobilschool.fintrack.data.repository

import androidx.lifecycle.LiveData
import com.mobilschool.fintrack.data.entity.TemplateFull
import com.mobilschool.fintrack.data.entity.TransactionFull
import com.mobilschool.fintrack.data.source.local.dao.CategoryDao
import com.mobilschool.fintrack.data.source.local.dao.TemplateDao
import com.mobilschool.fintrack.data.source.local.dao.TransactionDao
import com.mobilschool.fintrack.data.source.local.entity.*
import kotlinx.coroutines.experimental.launch
import java.util.*
import javax.inject.Inject

class TransactionRepository @Inject constructor(val transactionDao: TransactionDao,
                                                val categoryDao: CategoryDao,
                                                val templateDao: TemplateDao) {

    fun getLasyTransactionsForWallet(walletId: Int, n: Int): LiveData<List<TransactionFull>> {
        return transactionDao.getTransactionsForWallet(walletId, n)
    }

    fun insertOrUpdateTransaction(transaction: Transaction){
        launch {
            transactionDao.insertOrUpdate(transaction)
        }

    }

    fun insertAllTransactions(transactions: List<Transaction>){
        launch {
            transactionDao.insertOrUpdateAll(transactions)
        }

    }

    fun getCategoriesByType(type: TransactionType): LiveData<List<Category>>{
        return categoryDao.getCategoriesByType(type)
    }

    fun getAllPeriodicTransactions(): LiveData<List<TemplateFull>>{
        return templateDao.getAllPeriodicTransactions()
    }

    fun getUnexecutedPeriodicTransactions(): LiveData<List<TemplateFull>> {
        return templateDao.getPendingPeriodicTransactions(Date().time)
    }

    fun getAllTemplates() = templateDao.getAllTemplates()

    fun updatePeriodicTransactionLastExecution(id: Int, newDate: Long) {
        launch {
            templateDao.updatePeriodicTransactionLastExecution(id, newDate)
        }
    }

    fun insertTemplate(trans: Template) {
        launch {
            templateDao.insertOrUpdate(trans)
        }
    }

    fun getAllTemplatesByWalletId(walId: Int): LiveData<List<TemplateFull>> {
        return templateDao.getAllTemplatesByWalletId(walId)
    }

    fun getTemplateById(id: Int): LiveData<TemplateFull> {
        return templateDao.getTemplateById(id)
    }

    fun deleteTemplateWithId(id: Int) {
        launch {
            templateDao.deleteWithId(id)
        }
    }


}