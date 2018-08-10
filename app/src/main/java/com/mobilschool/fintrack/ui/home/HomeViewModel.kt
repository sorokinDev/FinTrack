package com.mobilschool.fintrack.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mobilschool.fintrack.data.entity.WalletFull
import com.mobilschool.fintrack.data.interactor.WalletInteractor
import com.mobilschool.fintrack.data.source.local.entity.Wallet
import com.mobilschool.fintrack.ui.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        val walletInteractor: WalletInteractor
): BaseViewModel() {

    val wallets: LiveData<List<WalletFull>> = walletInteractor.getAllWallets()

    val selectedWalletId: MutableLiveData<Int> = MutableLiveData()

    val selectedWallet: LiveData<WalletFull> = Transformations.switchMap(selectedWalletId){
        return@switchMap walletInteractor.getWalletById(it)
    }

    val templates = Transformations.switchMap(selectedWalletId){
        return@switchMap walletInteractor.getAllTemplatesByWalletId(it)
    }

    val balanceInFavoriteCurrencies = Transformations.switchMap(selectedWallet){
        return@switchMap walletInteractor.getWalletBalanceInFavoriteCurrencies(it.wallet)
    }

    val transactions = Transformations.switchMap(selectedWallet){
        return@switchMap walletInteractor.getLastNTransacionsForWallet(it.wallet.id, 100)
    }

    fun executePendingTransactions(){
        walletInteractor.executePendingTransactions()
    }
}