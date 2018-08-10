package com.mobilschool.fintrack.ui.template.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mobilschool.fintrack.data.entity.WalletFull
import com.mobilschool.fintrack.data.interactor.WalletInteractor
import com.mobilschool.fintrack.data.source.local.entity.Template
import com.mobilschool.fintrack.data.source.local.entity.Transaction
import com.mobilschool.fintrack.data.source.local.entity.TransactionType
import com.mobilschool.fintrack.ui.base.BaseViewModel
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TemplateAddViewModel @Inject constructor(
        val walletInteractor: WalletInteractor
) : BaseViewModel() {
    val wallets = walletInteractor.getAllWallets()

    var walletId = MutableLiveData<Int>()
    var wallet: LiveData<WalletFull> = Transformations.switchMap(walletId){
        Timber.i("${it} HELLO")
        return@switchMap walletInteractor.getWalletById(it)
    }

    val transactionType = MutableLiveData<TransactionType>().apply { value = TransactionType.EXPENSE }

    val categories = Transformations.switchMap(transactionType){
        walletInteractor.getTransactionCategoriesByType(it)
    }
    val selectedCategoryId = MutableLiveData<Int>()

    val isPeriodic = MutableLiveData<Boolean>().apply { value = false }

    fun addPeriodicTemplate(amount: Double, period: Int) {
        Timber.i(wallet.value!!.currencySymbol)
        Timber.i(walletId.value!!.toString())
        Timber.i(selectedCategoryId.value!!.toString())
        Timber.i(transactionType.value!!.toString())
        val newTransaction = Transaction(0, wallet.value!!.wallet.currencyId, walletId.value!!, Date().time, amount, selectedCategoryId.value!!, transactionType.value!!)
        walletInteractor.insertTransaction(newTransaction)
        walletInteractor.addTemplate(
                Template(
                        0, wallet.value!!.wallet.currencyId, walletId.value!!, Date().time, amount,
                        selectedCategoryId.value!!, transactionType.value!!, TimeUnit.DAYS.toMillis(period.toLong())
                )
        )
    }

    fun onWalletidChanged() {
        walletId.value = 1
    }

    fun addTemplate(amount: Double){
        walletInteractor.addTemplate(
                Template(
                        0, wallet.value!!.wallet.currencyId, walletId.value!!, Date().time, amount,
                        selectedCategoryId.value!!, transactionType.value!!, 0
                )
        )
    }

}