package com.mobilschool.fintrack.data.source.remote.api

import com.mobilschool.fintrack.data.source.remote.entity.ExchangeRate
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {

    @GET("latest")
    fun getRate(@Query("base") base: String, @Query("symbols") symbols: String): Single<ExchangeRate>

    companion object {
        val BASE_URL = "https://exchangeratesapi.io/api/"
    }
}