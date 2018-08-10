package com.mobilschool.fintrack.ui.transaction.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mobilschool.fintrack.data.entity.WalletFull
import com.mobilschool.fintrack.data.interactor.WalletInteractor
import com.mobilschool.fintrack.data.source.local.entity.*
import com.mobilschool.fintrack.ui.base.BaseViewModel
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TransactionAddViewModel @Inject constructor(val walletInteractor: WalletInteractor): BaseViewModel() {
    val walletId: MutableLiveData<Int> = MutableLiveData()
    val wallet: LiveData<WalletFull> = Transformations.switchMap(walletId){
        return@switchMap walletInteractor.getWalletById(it)
    }

    val transactionType = MutableLiveData<TransactionType>().apply { value = TransactionType.EXPENSE }
    val categories = Transformations.switchMap(transactionType){
        walletInteractor.getTransactionCategoriesByType(it)
    }

    val selectedCategoryId = MutableLiveData<Int>()

    val templateId = MutableLiveData<Int>()
    val template = Transformations.switchMap(templateId, {
        walletInteractor.getTemplateById(it)
    })


    fun addTransaction(amount: Double) {
        val newTransaction = Transaction(0, wallet.value!!.wallet.currencyId, walletId.value!!, Date().time, amount, selectedCategoryId.value!!, transactionType.value!!)
        walletInteractor.insertTransaction(newTransaction)
    }

}