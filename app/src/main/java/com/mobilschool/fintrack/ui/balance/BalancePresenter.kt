package com.mobilschool.fintrack.ui.balance

import com.mobilschool.fincalc.Operations
import com.mobilschool.fintrack.FinTrackerApplication
import com.mobilschool.fintrack.data.model.Repository
import com.mobilschool.fintrack.service.ExchangeRatesService
import com.mobilschool.fintrack.util.Utils
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class BalancePresenter(var view: BalanceContract.View?, val repository: Repository?) : BalanceContract.Presenter {


    private val exchangeRatesService: ExchangeRatesService = ExchangeRatesService.ratesService
    val ERROR_RECIEVE_RATE = 0.0

    override fun getBalance(to: String): Single<Double?> {

        val account = FinTrackerApplication.mockAccounts.first { it.uniqueName == FinTrackerApplication.CURRENT_ACCOUNT }
        val records = Utils.convertToRecords(account.transactions)
        val currentBalance = Operations.sumAllRecords(records, FinTrackerApplication.BASE_CURRENCY)

        val to = to.toUpperCase()
        if (to == FinTrackerApplication.BASE_CURRENCY) return Single.just(currentBalance)

        return exchangeRatesService.getRate(FinTrackerApplication.BASE_CURRENCY, to)
                .map { it.rates[to]?.times(currentBalance) }
                .subscribeOn(Schedulers.io())
                .onErrorReturnItem(ERROR_RECIEVE_RATE)
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getBalanceByCategories(): Observable<Pair<String, Double>> {

        val account = FinTrackerApplication.mockAccounts.first { it.uniqueName == FinTrackerApplication.CURRENT_ACCOUNT }

        val transaction = account.transactions.map {
            it.category to Utils.convertToCurrency(it.currencyName, it.amount.toDouble()).convertTo(FinTrackerApplication.BASE_CURRENCY).amount
        }.toList()

        return Observable.fromIterable(transaction)

    }

    override fun getSumWithoutIncomeOutcome(): Single<Double> {
        val account = FinTrackerApplication.mockAccounts.first { it.uniqueName == FinTrackerApplication.CURRENT_ACCOUNT }
        val records = Utils.convertToRecords(account.transactions)
        return Single.just(Operations.sumAllOperations(records, FinTrackerApplication.BASE_CURRENCY))
    }



    override fun attachView(view: BalanceContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}

