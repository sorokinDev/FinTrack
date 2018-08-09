package com.mobilschool.fintrack.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.mobilschool.fintrack.data.source.local.entity.Transaction
import com.mobilschool.fintrack.data.source.local.entity.WalletType

open class TransactionAdditionalInfo(

        @ColumnInfo(name = "currency_symbol")
        val currencySymbol: String,

        @ColumnInfo(name = "wallet_name")
        val walletName: String,

        @ColumnInfo(name = "wallet_type")
        val walletType: WalletType,

        @ColumnInfo(name = "category_name")
        val categoryName: String,

        @ColumnInfo(name = "category_img_res")
        val categoryImgRes: Int

)