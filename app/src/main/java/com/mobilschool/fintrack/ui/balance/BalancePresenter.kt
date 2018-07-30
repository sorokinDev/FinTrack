package com.mobilschool.fintrack.ui.balance

import com.mobilschool.fintrack.service.ExchangeRatesService
import com.mobilschool.fintrack.FinTrackerApplication
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


class BalancePresenter(var view: BalanceContract.View) : BalanceContract.Presenter {

    init {
        view.presenter = this
    }

    private val exchangeRatesService: ExchangeRatesService = ExchangeRatesService.ratesService

    private val currentBalance = 50.0
    val ERROR_RECIEVE_RATE = -1.0

    override fun getBalance(to: String): Single<Double?> {

        val to = to.toUpperCase()

        if (to == FinTrackerApplication.BASE_CURRENCY) return Single.just(currentBalance)

        return exchangeRatesService.getRate(FinTrackerApplication.BASE_CURRENCY, to)
                .map { it.rates[to]?.times(currentBalance) }
                .subscribeOn(Schedulers.io())
                .onErrorReturnItem(ERROR_RECIEVE_RATE)
    }

    override fun getBalanceByCategories(): Map<String, Double> {
        return mapOf("Продукты" to 10.0, "Зарплата" to 10.0, "ЖКХ" to 30.0)
    }
}

