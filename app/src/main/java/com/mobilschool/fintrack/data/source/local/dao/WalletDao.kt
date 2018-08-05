package com.mobilschool.fintrack.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mobilschool.fintrack.data.source.local.entity.Wallet

@Dao
interface WalletDao: BaseDao<Wallet> {

    @Query("SELECT * FROM wallets")
    fun getAllWallets(): LiveData<List<Wallet>>

    @Query("SELECT * FROM wallets WHERE id = :walletId")
    fun getWalletById(walletId: Int): LiveData<Wallet>


}