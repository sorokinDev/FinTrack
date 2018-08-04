package com.mobilschool.fintrack.di.module

import com.mobilschool.fintrack.data.source.remote.api.ExchangeRateApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideExchangeRateApi(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory,
                               callAdapterFactory: CallAdapter.Factory): ExchangeRateApi = Retrofit.Builder()
            .baseUrl(ExchangeRateApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .build()
            .create(ExchangeRateApi::class.java)

}