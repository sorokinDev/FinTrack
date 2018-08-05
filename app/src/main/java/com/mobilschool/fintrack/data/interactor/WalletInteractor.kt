package com.mobilschool.fintrack.data.interactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mobilschool.fintrack.data.common.DBResource
import com.mobilschool.fintrack.data.common.ResFailure
import com.mobilschool.fintrack.data.common.ResLoading
import com.mobilschool.fintrack.data.common.ResSuccess
import com.mobilschool.fintrack.data.repository.CurrencyRepository
import com.mobilschool.fintrack.data.repository.WalletRepository
import com.mobilschool.fintrack.data.source.local.entity.MoneyCurrency
import com.mobilschool.fintrack.data.source.local.entity.Wallet
import com.mobilschool.fintrack.util.CurrencyAmountPair
import com.mobilschool.fintrack.util.CurrencyPair
import javax.inject.Inject

class WalletInteractor @Inject constructor(
        private val currencyRepository: CurrencyRepository,
        private val walletRepository: WalletRepository
) {

    fun getAllWallets(): LiveData<List<Wallet>>{
        return walletRepository.getAllWallets()
    }

    fun getWalletById(walletId: Int): LiveData<Wallet> {
        return walletRepository.getWalletById(walletId)
    }

    fun exchangeMoney(amount: Double, baseCur: String, curr: List<String>): LiveData<DBResource<List<CurrencyAmountPair>>> {
        return Transformations.map(
                currencyRepository.getExchangeRates(baseCur, curr)
        ){ res ->
            when(res){
                is ResSuccess -> {
                    val exchangedList = mutableListOf<Pair<String, Double>>()
                    res.data.forEach {
                        exchangedList.add(Pair(it.currencies.second, amount * it.rate))
                    }
                    return@map ResSuccess<List<CurrencyAmountPair>>(exchangedList)
                }
                is ResLoading -> {
                    val exchangedList = mutableListOf<Pair<String, Double>>()
                    res.data?.forEach {
                        exchangedList.add(Pair(it.currencies.second, amount * it.rate))
                    }
                    return@map ResLoading<List<CurrencyAmountPair>>(exchangedList)
                }
                is ResFailure -> {
                    return@map ResFailure<List<CurrencyAmountPair>>(res.exception)
                }
            }
        }
    }

    fun getWalletBalanceInFavoriteCurrencies(wallet: Wallet): LiveData<DBResource<List<CurrencyAmountPair>>> {
        return Transformations.switchMap(currencyRepository.getFavoriteCurrencies()){ favCurs ->
            return@switchMap exchangeMoney(wallet.balance, wallet.currency, favCurs.map { it.code })
        }
    }


}