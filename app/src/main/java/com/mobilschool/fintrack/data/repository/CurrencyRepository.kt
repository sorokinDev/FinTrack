package com.mobilschool.fintrack.data.repository

import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.mobilschool.fintrack.data.common.*
import com.mobilschool.fintrack.data.source.local.dao.CurrencyDao
import com.mobilschool.fintrack.data.source.local.dao.ExchangeDao
import com.mobilschool.fintrack.data.source.local.entity.Currency
import com.mobilschool.fintrack.data.source.local.entity.ExchangeRate
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

    fun getAllCurrencies(): LiveData<List<Currency>> {
        return currencyDao.getAllCurrencies()
    }

    fun getFavoriteCurrencies(): LiveData<List<Currency>> {
        return currencyDao.getFavoriteCurrencies()
    }


    fun getExchangeRates(baseCur: String, currencies: List<String>): LiveData<List<ExchangeRate>> {

        val date = Date().time

        val currencyPairList = mutableListOf<CurrencyPair>()

        currencies.forEach {
            currencyPairList.add(Pair(baseCur, it))
        }

        val dbSource =  exchangeDao.getExchangeRates(currencies.map { "${baseCur}_${it}" })

        val dbObserver = object : Observer<List<ExchangeRate>> {
            override fun onChanged(dbData: List<ExchangeRate>?) {
                dbSource.removeObserver(this)
                var shouldRefresh = false

                Timber.i("IN OBSERVER")

                if(dbData == null || dbData.size < currencyPairList.size || dbData.any { date - it.date > EXCHANGE_RATE_LIFETIME }) {
                    Timber.i("WILL REFRESH")
                    shouldRefresh = true
                }

                if(shouldRefresh){
                    exchangeApi.getRate(baseCur, currencies.joinToString(",")).enqueue(object : Callback<RemoteExchangeRate> {
                        override fun onFailure(call: Call<RemoteExchangeRate>?, t: Throwable?) {
                            Timber.e(t!!)
                        }

                        override fun onResponse(call: Call<RemoteExchangeRate>?, response: Response<RemoteExchangeRate>?) {
                            if(response != null && response.isSuccessful && response.body() != null){
                                val remData = response.body()
                                launch {
                                    val resList = mutableListOf<ExchangeRate>()
                                    remData?.rates?.forEach { curToRate ->
                                        resList.add(ExchangeRate("${baseCur}_${curToRate.key}", curToRate.value, Date().time))
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