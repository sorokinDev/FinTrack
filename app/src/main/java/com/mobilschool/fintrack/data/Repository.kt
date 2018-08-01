package com.mobilschool.fintrack.data.model

import com.mobilschool.fintrack.FinTrackerApplication
import io.reactivex.Observable
import io.reactivex.Single

class Repository {

    fun addTransaction(transcation: Transaction) {
        TODO()
    }

    fun getTransactions(): Single<List<Transaction>> {
        val account = FinTrackerApplication.mockAccounts.first { it.uniqueName == FinTrackerApplication.CURRENT_ACCOUNT }
        return Single.just(account.transactions)
    }

    fun getBalance(): Single<Double?> {
        TODO()
    }

    fun getBalanceByCategories(): Observable<Pair<String, Double>> {
        TODO()
    }
}