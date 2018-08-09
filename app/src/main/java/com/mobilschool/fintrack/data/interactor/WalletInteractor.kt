package com.mobilschool.fintrack.data.interactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.mobilschool.fintrack.data.entity.TemplateFull
import com.mobilschool.fintrack.data.entity.TransactionFull
import com.mobilschool.fintrack.data.entity.WalletFull
import com.mobilschool.fintrack.data.repository.CurrencyRepository
import com.mobilschool.fintrack.data.repository.TransactionRepository
import com.mobilschool.fintrack.data.repository.WalletRepository
import com.mobilschool.fintrack.data.source.local.entity.*
import com.mobilschool.fintrack.data.source.local.entity.Currency
import com.mobilschool.fintrack.util.CurrencyAmountPair
import kotlinx.coroutines.experimental.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class WalletInteractor @Inject constructor(
        private val currencyRepository: CurrencyRepository,
        private val walletRepository: WalletRepository,
        private val transactionRepository: TransactionRepository
) {

    fun getAllWallets(): LiveData<List<WalletFull>>{
        return walletRepository.getAllWallets()
    }

    fun getWalletById(walletId: Int): LiveData<WalletFull> {
        return walletRepository.getWalletById(walletId)
    }

    // TODO: REFACTOR
    fun exchangeMoney(amount: Double, baseCur: String, curr: List<Currency>): LiveData<List<CurrencyAmountPair>> {
        return Transformations.map(
                currencyRepository.getExchangeRates(baseCur, curr.map { it.id })
        ) { repoRes ->
            val res = mutableListOf<CurrencyAmountPair>()
            curr.forEach {c ->
                res.add(CurrencyAmountPair(c, (repoRes.firstOrNull{ it.id.split("_")[0] == baseCur && it.id.split("_")[1] == c.id }?.rate ?:
                    (if (c.id == baseCur) 1.0 else 0.0)) * amount))
            }

            return@map res
        }
    }

    fun getWalletBalanceInFavoriteCurrencies(wallet: Wallet): LiveData<List<CurrencyAmountPair>> {
        return Transformations.switchMap(currencyRepository.getFavoriteCurrencies()){ favCurs ->
            return@switchMap exchangeMoney(wallet.balance, wallet.currencyId,
                                favCurs.filter { it.id != wallet.currencyId })
        }
    }

    fun insertTransaction(transaction: Transaction){
        transactionRepository.insertOrUpdateTransaction(transaction)
        walletRepository.changeBalance(transaction.walletId, transaction.amount *
                (if (transaction.type == TransactionType.INCOME) 1 else (-1)))
    }

    fun insertAllTransactions(transactions: List<Transaction>){
        transactionRepository.insertAllTransactions(transactions)
        transactions.groupBy { it.walletId }.forEach{byWalId ->
            walletRepository.changeBalance(byWalId.key, byWalId.value.sumByDouble {
                it.amount * (if (it.type == TransactionType.INCOME) 1 else (-1))
            })
        }

    }

    fun getLastNTransacionsForWallet(walletId: Int, n: Int): LiveData<List<TransactionFull>> {
        return transactionRepository.getLasyTransactionsForWallet(walletId, n)
    }

    fun getTransactionCategoriesByType(type: TransactionType): LiveData<List<Category>>{
        return transactionRepository.getCategoriesByType(type)
    }

    // TODO: change date logic
    fun executePendingTransactions(){
        val unexec = transactionRepository.getUnexecutedPeriodicTransactions()
        val obs = object : Observer<List<TemplateFull>> {
            override fun onChanged(trans: List<TemplateFull>?) {
                unexec.removeObserver(this)
                Timber.i("in observer")
                launch {
                    val nowTime = Date().time
                    val transToAdd = mutableListOf<Transaction>()
                    Timber.i("before foreach")

                    trans?.forEach {
                        Timber.i("Adding periodic")
                        var newTime = it.transaction.date + it.transaction.period
                        while (newTime <= nowTime){
                            transToAdd.add(Transaction(0, it.transaction.currencyId, it.transaction.walletId, newTime, it.transaction.amount, it.transaction.categoryId, it.transaction.type))
                            newTime += it.transaction.period
                        }
                        transactionRepository.updatePeriodicTransactionLastExecution(it.transaction.id, newTime)
                    }

                    insertAllTransactions(transToAdd)
                }
            }
        }
        unexec.observeForever(obs)
    }

    fun addPeriodicTransaction(trans: Template){
        transactionRepository.insertTemplate(trans)
    }

    fun getAllTemplates(): LiveData<List<TemplateFull>> {
        return transactionRepository.getAllTemplates()
    }

    fun getAllPeriodics(): LiveData<List<TemplateFull>> {
        return transactionRepository.getAllPeriodicTransactions()
    }


}