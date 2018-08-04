package com.mobilschool.fintrack.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.mobilschool.fintrack.data.source.local.entity.Wallet
import io.reactivex.Flowable

@Dao
interface WalletDao: BaseDao<Wallet> {

    @Query("SELECT * FROM wallets")
    fun getAllWallets(): Flowable<List<Wallet>>

    @Query("SELECT * FROM wallets WHERE id = :walletId")
    fun getWalletById(walletId: Int): Flowable<Wallet>

}