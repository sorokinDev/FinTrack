package com.mobilschool.fintrack.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
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
        val EXCHANGE_RATE_LIFETIME = 1800
    }

    fun getAllCurrencies(): LiveData<List<MoneyCurrency>> {
        return currencyDao.getAllCurrencies()
    }

    fun getFavoriteCurrencies(): LiveData<List<MoneyCurrency>> {
        return currencyDao.getFavoriteCurrencies()
    }


    fun getExchangeRates(baseCur: String, currencies: List<String>): LiveData<DBResource<List<LocalExchangeRate>>> {
        val result = MediatorLiveData<DBResource<List<LocalExchangeRate>>>()

        val date = Date()

        val currencyPairList = mutableListOf<CurrencyPair>()
        currencies.forEach {
            currencyPairList.add(Pair(baseCur, it))
        }

        val dbSource = exchangeDao.getExchangeRates(currencyPairList)

        val networkSource = MutableLiveData<DBResource<RemoteExchangeRate>>()

        // TODO: refactor nullability
        result.addSource(dbSource) {data ->
            Timber.i("DB SOURCE Observer")
            var shouldRefresh = false
            if(data.isEmpty()) {
                shouldRefresh = true
                result.postValue(ResLoading())
            }else if (data.any { date.diff(it.date) > EXCHANGE_RATE_LIFETIME }){
                shouldRefresh = true
                result.postValue(ResLoading(data))
                result.removeSource(networkSource)
            }

            if(!shouldRefresh) {
                result.postValue(ResSuccess(data))
                result.removeSource(dbSource)
            }else{
                result.removeSource(networkSource)
                result.addSource(networkSource){ remData ->
                    Timber.i("Rem data source observer")
                    launch {
                        when(remData){
                            is ResSuccess -> {
                                val resList = mutableListOf<LocalExchangeRate>()
                                remData.data.rates.forEach { curToRate ->
                                    resList.add(LocalExchangeRate(CurrencyPair(baseCur, curToRate.key), curToRate.value, Date()))
                                }
                                exchangeDao.insertOrUpdateAll(resList)
                            }
                        }
                    }

                }

                Timber.i(baseCur + " " + currencies.joinToString(","))
                exchangeApi.getRate(baseCur, currencies.joinToString(",")).enqueue(object : Callback<RemoteExchangeRate> {
                    override fun onFailure(call: Call<RemoteExchangeRate>?, t: Throwable?) {
                        networkSource.value = ResFailure(t!!, null)
                        Timber.e(t!!)
                    }

                    override fun onResponse(call: Call<RemoteExchangeRate>?, response: Response<RemoteExchangeRate>?) {
                        Timber.i("Response")
                        Timber.i(response?.headers()?.toString())
                        Timber.i(response?.message())
                        if(response != null && response.isSuccessful){
                            Timber.i("Success response")
                            networkSource.postValue(ResSuccess(response.body()!!))
                        }
                    }

                })
                result.postValue(ResLoading(data))
            }
        }

        return result
    }



}