package com.mobilschool.fintrack.service

import com.mobilschool.fintrack.BuildConfig
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesService {

    @GET("latest")
    fun getRate(@Query("base") base: String, @Query("symbols") symbols: String): Single<CurrencyRate>

    companion object Factory {

        private val url = "https://exchangeratesapi.io/api/"

        val ratesService: ExchangeRatesService by lazy {

            val okClientBuilder = OkHttpClient().newBuilder()
            val loggingInterceptor = HttpLoggingInterceptor()

            if (BuildConfig.DEBUG) {
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }

            okClientBuilder.addInterceptor(loggingInterceptor)

            val retrofit = Retrofit.Builder()
                    .baseUrl(url)
                    .client(okClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

            retrofit.create(ExchangeRatesService::class.java)
        }
    }
}

data class CurrencyRate(
        val date: String,
        val base: String,
        val rates: Map<String, Double>
)




