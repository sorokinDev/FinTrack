package com.mobilschool.fintrack.ui.transactions

import com.mobilschool.fintrack.FinTrackerApplication
import com.mobilschool.fintrack.data.model.Repository
import com.mobilschool.fintrack.data.model.Transaction
import com.mobilschool.fintrack.ui.base.BasePresenter
import javax.inject.Inject

class TransactionsPresenter @Inject constructor(var repository: Repository?) : BasePresenter<TransactionsView>() {

    fun getOperations(): List<Transaction> {

        val account = FinTrackerApplication.mockAccounts.first { it.uniqueName == FinTrackerApplication.CURRENT_ACCOUNT }
        return account.transactions

    }
}