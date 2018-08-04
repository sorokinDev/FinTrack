package com.mobilschool.fintrack.data.source.remote.api

import com.mobilschool.fintrack.data.source.remote.entity.RemoteExchangeRate
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {

    // Symbols is comma separated currency codes for specific rates, for example: "RUB,USD,EUR"
    @GET("latest")
    fun getRate(@Query("base") base: String, @Query("symbols") symbols: String): Single<RemoteExchangeRate>

    companion object {
        val BASE_URL = "https://exchangeratesapi.io/api/"
    }
}