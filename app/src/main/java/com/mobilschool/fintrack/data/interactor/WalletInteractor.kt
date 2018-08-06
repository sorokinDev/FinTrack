package com.mobilschool.fintrack.data.interactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mobilschool.fintrack.data.repository.CurrencyRepository
import com.mobilschool.fintrack.data.repository.TransactionRepository
import com.mobilschool.fintrack.data.repository.WalletRepository
import com.mobilschool.fintrack.data.source.local.entity.*
import com.mobilschool.fintrack.util.CurrencyAmountPair
import javax.inject.Inject

class WalletInteractor @Inject constructor(
        private val currencyRepository: CurrencyRepository,
        private val walletRepository: WalletRepository,
        private val transactionRepository: TransactionRepository
) {

    fun getAllWallets(): LiveData<List<Wallet>>{
        return walletRepository.getAllWallets()
    }

    fun getWalletById(walletId: Int): LiveData<Wallet> {
        return walletRepository.getWalletById(walletId)
    }

    fun exchangeMoney(amount: Double, baseCur: String, curr: List<String>): LiveData<List<CurrencyAmountPair>> {
        return Transformations.map(
                currencyRepository.getLocalExchangeRates(baseCur, curr)
        ) { repoRes ->
            val res = mutableListOf<CurrencyAmountPair>()
            curr.forEach {c ->
                res.add(CurrencyAmountPair(c, (repoRes.firstOrNull{ it.currencies.first == baseCur && it.currencies.second == c }?.rate ?:
                    (if (c == baseCur) 1.0 else 0.0)) * amount))
            }

            return@map res
        }
    }

    fun getWalletBalanceInFavoriteCurrencies(wallet: Wallet): LiveData<List<CurrencyAmountPair>> {
        return Transformations.switchMap(currencyRepository.getFavoriteCurrencies()){ favCurs ->
            return@switchMap exchangeMoney(wallet.balance, wallet.currency,
                                favCurs.filter { it.code != wallet.currency }.map { it.code })
        }
    }

    fun insertTransaction(transaction: MoneyTransaction){
        transactionRepository.insertOrUpdateTransaction(transaction)
        walletRepository.changeBalance(transaction.walletId, transaction.amount *
                (if (transaction.type == TransactionType.INCOME) 1 else (-1)))
    }

    fun getLastNTransacionsForWallet(n: Int, walletId: Int): LiveData<List<MoneyTransactionWithCategory>> {
        return transactionRepository.getLastNTransactionsForWallet(n, walletId)
    }

    fun getTransactionCategoriesByType(type: TransactionType): LiveData<List<TransactionCategory>>{
        return transactionRepository.getCategoriesByType(type)
    }


}