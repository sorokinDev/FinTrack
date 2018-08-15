package com.mobilschool.fintrack.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mobilschool.fintrack.data.entity.WalletFull
import com.mobilschool.fintrack.data.source.local.entity.Wallet

@Dao
interface WalletDao: BaseDao<Wallet> {

    @Query("SELECT wallets.*, currencies.symbol AS currency_symbol FROM wallets INNER JOIN currencies ON currencies.id = wallets.currency_id")
    fun getAllWallets(): LiveData<List<WalletFull>>

    @Query("SELECT wallets.*, currencies.symbol AS currency_symbol FROM wallets INNER JOIN currencies ON currencies.id = wallets.currency_id WHERE wallets.id = :id LIMIT 1")
    fun getWalletById(id: Int): LiveData<WalletFull>

    @Query("UPDATE wallets SET balance = (balance + :amount) WHERE id = :walletId")
    fun changeBalance(walletId: Int, amount: Double)

}