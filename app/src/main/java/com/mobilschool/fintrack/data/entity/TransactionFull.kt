package com.mobilschool.fintrack.data.entity

import androidx.room.Embedded
import com.mobilschool.fintrack.data.source.local.entity.Transaction
import com.mobilschool.fintrack.data.source.local.entity.WalletType

class TransactionFull(
        @Embedded
        val transaction: Transaction,
        currencySymbol: String,
        walletName: String,
        walletType: WalletType,
        categoryName: String,
        categoryImgRes: Int
) : TransactionAdditionalInfo(currencySymbol, walletName, walletType, categoryName, categoryImgRes)