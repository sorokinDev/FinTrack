package com.mobilschool.fintrack.data.repository

import com.mobilschool.fintrack.data.source.local.entity.MoneyCurrency
import com.mobilschool.fintrack.data.source.local.entity.MoneyCurrency_
import com.mobilschool.fintrack.util.RxQuery
import io.objectbox.Box
import javax.inject.Inject

class CurrencyRepository @Inject constructor(val currencyBox: Box<MoneyCurrency>) {

    fun getAllCurrencies() =
            RxQuery.single(currencyBox.query().build ())

    fun getFavoriteCurrencies() =
            RxQuery.single(currencyBox.query().equal(MoneyCurrency_.isFavorite, true).build())

}