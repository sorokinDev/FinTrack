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

    private var wallets: LiveData<List<WalletFull>> = walletInteractor.getAllWallets()
    private var selectedWalletId: MutableLiveData<Int> = MutableLiveData()

    private var selectedWallet: LiveData<WalletFull> = Transformations.switchMap(selectedWalletId){
        return@switchMap walletInteractor.getWalletById(it)
    }

    private var balanceInFavoriteCurrencies = Transformations.switchMap(selectedWallet){
        return@switchMap walletInteractor.getWalletBalanceInFavoriteCurrencies(it.wallet)
    }

    private var transactions = Transformations.switchMap(selectedWallet){
        return@switchMap walletInteractor.getLastNTransacionsForWallet(it.wallet.id, 100)
    }


    fun getWallets() = wallets

    fun getSelectedWalletId() = selectedWalletId

    fun setSelectedWalletId(sel: Int){
        selectedWalletId.value = sel
    }

    fun getSelectedWallet() = selectedWallet

    fun getBalanceInFavoriteCurrencies() = balanceInFavoriteCurrencies

    fun getTransactions() = transactions

    fun executePendingTransactions(){
        walletInteractor.executePendingTransactions()
    }
}