package com.mobilschool.fintrack.ui.home

import com.mobilschool.fintrack.data.source.local.entity.Wallet
import com.mobilschool.fintrack.ui.base.BaseView
import com.mobilschool.fintrack.util.CurrencyAmountPair

interface HomeView: BaseView {
    fun setWallets(it: List<Wallet>)
    fun setBalanceInCurrencies(walletInCurrencies: List<CurrencyAmountPair>)

}
