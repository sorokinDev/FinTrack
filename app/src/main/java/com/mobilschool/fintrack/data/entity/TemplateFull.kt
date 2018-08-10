package com.mobilschool.fintrack.data.entity

import androidx.room.Embedded
import com.mobilschool.fintrack.data.source.local.entity.Template
import com.mobilschool.fintrack.data.source.local.entity.WalletType

class TemplateFull(
        @Embedded
        val transaction: Template,
        currencySymbol: String,
        walletName: String,
        walletType: WalletType,
        categoryName: String,
        categoryImgRes: Int
) : TransactionAdditionalInfo(currencySymbol, walletName, walletType, categoryName, categoryImgRes)