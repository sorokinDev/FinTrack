package com.mobilschool.fintrack.data.repository

import com.mobilschool.fintrack.data.source.local.dao.CurrencyDao
import com.mobilschool.fintrack.data.source.local.dao.ExchangeDao
import com.mobilschool.fintrack.data.source.local.entity.LocalExchangeRate
import com.mobilschool.fintrack.data.source.remote.api.ExchangeRateApi
import com.mobilschool.fintrack.util.diff
import io.reactivex.Flowable
import io.reactivex.exceptions.Exceptions
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
        val currencyDao: CurrencyDao,
        val exchangeDao: ExchangeDao,
        val exchangeApi: ExchangeRateApi) {
    companion object {
        val EXCHANGE_RATE_LIFETIME = 1800
    }

    fun getAllCurrencies() = currencyDao.getAllCurrencies()

    fun getFavoriteCurrencies() = currencyDao.getFavoriteCurrencies()

    fun getExchangeRate(currencies: Pair<String, String>): Flowable<LocalExchangeRate> {
        val date = Date()

        val local = exchangeDao.getExchangeRate(currencies).map {
            if(it.isEmpty()){
                LocalExchangeRate(currencies, 0.0, Date(0))
            }else{
                it[0]
            }
        }

        val remote = local.filter {
            date.diff(it.date) > EXCHANGE_RATE_LIFETIME
        }.flatMapSingle { lc ->
            exchangeApi.getRate(lc.currencies.first, "")
        }.map { remote ->
            LocalExchangeRate(currencies, remote.rates.getOrDefault(currencies.second, 0.0), date)
        }.doOnNext { localUnsaved ->
            exchangeDao.insertOrUpdate(localUnsaved)
        }.doOnError {error ->
            Timber.e(error)
            throw Exceptions.propagate(error)
        }.filter { false }

        return Flowable.mergeDelayError(local, remote)
    }


}