package com.mobilschool.fintrack.data.source.remote.api

import com.mobilschool.fintrack.data.source.remote.entity.RemoteExchangeRate
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {

    // Symbols is comma separated currency codes for specific rates, for example: "RUB,USD,EUR"
    @GET("/api/latest")
    fun getRate(@Query("base") base: String, @Query("symbols") symbols: String): Call<RemoteExchangeRate>

    companion object {
        val BASE_URL = "https://exchangeratesapi.io"
    }
}