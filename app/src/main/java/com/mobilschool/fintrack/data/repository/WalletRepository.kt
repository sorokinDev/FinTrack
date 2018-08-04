package com.mobilschool.fintrack.data.repository

import com.mobilschool.fintrack.data.source.local.dao.WalletDao
import com.mobilschool.fintrack.data.source.local.entity.Wallet
import io.reactivex.Flowable
import javax.inject.Inject

class WalletRepository @Inject constructor(val walletDao: WalletDao) {

    fun getAllWallets(): Flowable<List<Wallet>>{
        return walletDao.getAllWallets()
    }

    fun getWalletById(walletId: Int): Flowable<Wallet>{
        return walletDao.getWalletById(walletId)
    }

}