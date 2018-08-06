package com.mobilschool.fintrack.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mobilschool.fintrack.data.interactor.WalletInteractor
import com.mobilschool.fintrack.data.source.local.entity.Wallet
import com.mobilschool.fintrack.ui.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        val walletInteractor: WalletInteractor
): BaseViewModel() {

    private var wallets: LiveData<List<Wallet>> = walletInteractor.getAllWallets()
    private var selectedWalletId: MutableLiveData<Int> = MutableLiveData()
    private var selectedWallet: LiveData<Wallet> = Transformations.switchMap(selectedWalletId){
        return@switchMap walletInteractor.getWalletById(it)
    }
    private var balanceInFavoriteCurrencies = Transformations.switchMap(selectedWallet){
        return@switchMap walletInteractor.getWalletBalanceInFavoriteCurrencies(it)
    }

    fun getWallets(): LiveData<List<Wallet>>{
        return wallets
    }

    fun getSelectedWalletId(): LiveData<Int>{
        return selectedWalletId
    }

    fun setSelectedWalletId(sel: Int){
        selectedWalletId.value = sel
    }

    fun getSelectedWallet() = selectedWallet

    fun getBalanceInFavoriteCurrencies() = balanceInFavoriteCurrencies
}