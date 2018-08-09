package com.mobilschool.fintrack.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mobilschool.fintrack.data.entity.TransactionFull
import com.mobilschool.fintrack.data.source.local.entity.Transaction

@Dao
interface TransactionDao: BaseDao<Transaction> {

    @Query(
"""SELECT transactions.*, wallets.name AS wallet_name, wallets.type as wallet_type, currencies.symbol AS currency_symbol, categories.name AS category_name, categories.img_res AS category_img_res
        FROM transactions
            INNER JOIN wallets ON transactions.wallet_id = wallets.id
            INNER JOIN currencies ON transactions.currency_id = currencies.id
            INNER JOIN categories ON transactions.category_id = categories.id
        WHERE wallet_id = :walletId
        ORDER BY transactions.date DESC
        LIMIT (:count)""")
    fun getTransactionsForWallet(walletId: Int, count: Int): LiveData<List<TransactionFull>>


}