package com.mobilschool.fintrack.ui.transactions

import com.mobilschool.fintrack.FinTrackerApplication
import com.mobilschool.fintrack.data.model.Repository
import com.mobilschool.fintrack.data.model.Transaction

class TransactionsPresenter(var view: TranscationsContract.View?, var repository: Repository?) : TranscationsContract.Presenter {

    override fun attachView(view: TranscationsContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getOperations(): List<Transaction> {

        val account = FinTrackerApplication.mockAccounts.first { it.uniqueName == FinTrackerApplication.CURRENT_ACCOUNT }
        return account.transactions

    }
}