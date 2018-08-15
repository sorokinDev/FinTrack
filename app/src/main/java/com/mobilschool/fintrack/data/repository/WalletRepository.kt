package com.mobilschool.fintrack.data.repository

import androidx.lifecycle.LiveData
import com.mobilschool.fintrack.data.entity.WalletFull
import com.mobilschool.fintrack.data.source.local.dao.WalletDao
import com.mobilschool.fintrack.data.source.local.entity.Wallet
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class WalletRepository @Inject constructor(val walletDao: WalletDao) {

    fun getAllWallets(): LiveData<List<WalletFull>>{
        return walletDao.getAllWallets()
    }

    fun getWalletById(walletId: Int): LiveData<WalletFull> {
        return walletDao.getWalletById(walletId)
    }

    fun changeBalance(walletId: Int, amount: Double){
        launch {
            walletDao.changeBalance(walletId, amount)
        }
    }

}