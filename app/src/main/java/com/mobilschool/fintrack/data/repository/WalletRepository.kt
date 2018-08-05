package com.mobilschool.fintrack.data.repository

import androidx.lifecycle.LiveData
import com.mobilschool.fintrack.data.source.local.dao.WalletDao
import com.mobilschool.fintrack.data.source.local.entity.Wallet
import javax.inject.Inject

class WalletRepository @Inject constructor(val walletDao: WalletDao) {

    fun getAllWallets(): LiveData<List<Wallet>>{
        return walletDao.getAllWallets()
    }

    fun getWalletById(walletId: Int): LiveData<Wallet>{
        return walletDao.getWalletById(walletId)
    }

}