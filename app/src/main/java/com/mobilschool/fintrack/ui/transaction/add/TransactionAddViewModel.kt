package com.mobilschool.fintrack.ui.transaction.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mobilschool.fintrack.data.interactor.WalletInteractor
import com.mobilschool.fintrack.data.source.local.entity.MoneyTransaction
import com.mobilschool.fintrack.data.source.local.entity.TransactionCategory
import com.mobilschool.fintrack.data.source.local.entity.TransactionType
import com.mobilschool.fintrack.data.source.local.entity.Wallet
import com.mobilschool.fintrack.ui.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class TransactionAddViewModel @Inject constructor(val walletInteractor: WalletInteractor): BaseViewModel() {
    private var walletId: MutableLiveData<Int> = MutableLiveData()
    private var wallet: LiveData<Wallet> = Transformations.switchMap(walletId){
        return@switchMap walletInteractor.getWalletById(it)
    }
    private var transactionType = MutableLiveData<TransactionType>().apply { value = TransactionType.EXPENSE }
    private var categories = Transformations.switchMap(transactionType){
        walletInteractor.getTransactionCategoriesByType(it)
    }

    private var selectedCategoryId = MutableLiveData<Int>()

    fun setWalletId(id: Int){
        walletId.value = id
    }

    fun getWallet(): LiveData<Wallet> {
        return wallet
    }

    fun setTransactionType(type: TransactionType) {
        transactionType.value = type
    }

    fun getTransactionType(): MutableLiveData<TransactionType> {
        return transactionType
    }

    fun addTransaction(amount: Double) {
        val newTransaction = MoneyTransaction(0, walletId.value!!, amount, Date(), transactionType.value!!, selectedCategoryId.value!!)
        walletInteractor.insertTransaction(newTransaction)
    }

    fun getCategories(): LiveData<List<TransactionCategory>> {
        return categories
    }

    fun getSelectedCategoryId(): LiveData<Int> {
        return selectedCategoryId
    }
    fun setSelectedCategoryId(id: Int) {
        selectedCategoryId.value = id
    }

}