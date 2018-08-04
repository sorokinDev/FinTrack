package com.mobilschool.fintrack.data.interactor

import com.mobilschool.fintrack.data.repository.CurrencyRepository
import com.mobilschool.fintrack.data.repository.WalletRepository
import com.mobilschool.fintrack.data.source.local.entity.Wallet
import com.mobilschool.fintrack.util.CurrencyPair
import io.reactivex.Flowable
import javax.inject.Inject

class WalletInteractor @Inject constructor(
        val currencyRepository: CurrencyRepository,
        val walletRepository: WalletRepository
) {

    fun getAllWallets(): Flowable<List<Wallet>>{
        return walletRepository.getAllWallets()
    }

    fun getWalletById(walletId: Int): Flowable<Wallet> {
        return walletRepository.getWalletById(walletId)
    }

    fun exchangeMoney(amount: Double, curr: CurrencyPair): Flowable<Double>? {
        return currencyRepository.getExchangeRate(curr).map { it.rate * amount }
    }



}