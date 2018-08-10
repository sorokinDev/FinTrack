package com.mobilschool.fintrack.data.repository

import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.mobilschool.fintrack.data.common.*
import com.mobilschool.fintrack.data.source.local.dao.CurrencyDao
import com.mobilschool.fintrack.data.source.local.dao.ExchangeDao
import com.mobilschool.fintrack.data.source.local.entity.LocalExchangeRate
import com.mobilschool.fintrack.data.source.local.entity.MoneyCurrency
import com.mobilschool.fintrack.data.source.remote.api.ExchangeRateApi
import com.mobilschool.fintrack.data.source.remote.entity.RemoteExchangeRate
import com.mobilschool.fintrack.util.CurrencyPair
import com.mobilschool.fintrack.util.diff
import kotlinx.coroutines.experimental.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
        val currencyDao: CurrencyDao,
        val exchangeDao: ExchangeDao,
        val exchangeApi: ExchangeRateApi) {
    companion object {
        // Half of hour
        val EXCHANGE_RATE_LIFETIME = 1800000
    }

    fun getAllCurrencies(): LiveData<List<MoneyCurrency>> {
        return currencyDao.getAllCurrencies()
    }

    fun getFavoriteCurrencies(): LiveData<List<MoneyCurrency>> {
        return currencyDao.getFavoriteCurrencies()
    }


    fun getLocalExchangeRates(baseCur: String, currencies: List<String>): LiveData<List<LocalExchangeRate>> {

        val date = Date()

        val currencyPairList = mutableListOf<CurrencyPair>()

        currencies.forEach {
            currencyPairList.add(Pair(baseCur, it))
        }

        val dbSource =  exchangeDao.getExchangeRates(currencyPairList)

        val dbObserver = object : Observer<List<LocalExchangeRate>> {
            override fun onChanged(dbData: List<LocalExchangeRate>?) {
                dbSource.removeObserver(this)
                var shouldRefresh = false

                Timber.i("IN OBSERVER")

                if(dbData == null || dbData.size < currencyPairList.size || dbData.any { date.diff(it.date) > EXCHANGE_RATE_LIFETIME }) {
                    Timber.i("WILL REFRESH")
                    shouldRefresh = true
                }

                if(shouldRefresh){
                    exchangeApi.getRate(baseCur, currencies.joinToString(",")).enqueue(object : Callback<RemoteExchangeRate> {
                        override fun onFailure(call: Call<RemoteExchangeRate>?, t: Throwable?) {
                            Timber.e(t!!)
                        }

                        override fun onResponse(call: Call<RemoteExchangeRate>?, response: Response<RemoteExchangeRate>?) {
                            Timber.i("IN ONRESPONSE")
                            if(response != null && response.isSuccessful && response.body() != null){
                                val remData = response.body()
                                Timber.i("Success response")
                                launch {
                                    val resList = mutableListOf<LocalExchangeRate>()
                                    remData?.rates?.forEach { curToRate ->
                                        resList.add(LocalExchangeRate(CurrencyPair(baseCur, curToRate.key), curToRate.value, Date()))
                                    }
                                    if(!resList.isEmpty()){
                                        exchangeDao.insertOrUpdateAll(resList)
                                    }
                                }
                            }
                        }

                    })
                }
            }

        }

        dbSource.observeForever(dbObserver)

        return dbSource
    }



}