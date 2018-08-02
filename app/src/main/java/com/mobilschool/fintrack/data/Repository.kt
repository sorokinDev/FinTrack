package com.mobilschool.fintrack.data.entity

import com.mobilschool.fintrack.FinTrackerApplication
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor() {

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