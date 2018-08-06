package com.mobilschool.fintrack.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mobilschool.fintrack.data.source.local.entity.MoneyTransaction
import com.mobilschool.fintrack.data.source.local.entity.MoneyTransactionWithCategory

@Dao
interface TransactionDao: BaseDao<MoneyTransaction> {

    @Query(
"""SELECT money_transactions.*, transaction_categories.name as categoryName FROM money_transactions
INNER JOIN transaction_categories ON money_transactions.category_id = transaction_categories.id WHERE walletId = (:walletId) ORDER BY date DESC LIMIT (:n)""")
    fun selectLastNTransactionsForWallet(n: Int, walletId: Int): LiveData<List<MoneyTransactionWithCategory>>
}