package com.mobilschool.fintrack.ui.home

import com.arellomobile.mvp.InjectViewState
import com.mobilschool.fintrack.data.common.ResFailure
import com.mobilschool.fintrack.data.common.ResLoading
import com.mobilschool.fintrack.data.common.ResSuccess
import com.mobilschool.fintrack.data.interactor.WalletInteractor
import com.mobilschool.fintrack.data.source.local.entity.Wallet
import com.mobilschool.fintrack.ui.base.BasePresenter
import com.mobilschool.fintrack.util.CurrencyAmountPair
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class HomePresenter @Inject constructor(val walletInteractor: WalletInteractor): BasePresenter<HomeView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        walletInteractor.getAllWallets().observeForever {
            viewState.setWallets(it)
        }
    }

    fun selectWallet(wallet: Wallet){
        walletInteractor.getWalletBalanceInFavoriteCurrencies(wallet).observeForever {
            when(it){
                is ResSuccess -> {
                    Timber.i("Success: " + it.data.toString())
                    viewState.setBalanceInCurrencies(it.data)
                }
                is ResLoading -> {
                    Timber.i("Loading: " + it.data.toString())
                    viewState.setBalanceInCurrencies(it.data ?: listOf(CurrencyAmountPair("LOAD", 0.0)))
                }
                is ResFailure -> {
                    Timber.e(it.exception)
                }
            }
        }


    }

}