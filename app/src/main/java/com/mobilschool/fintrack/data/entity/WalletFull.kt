package com.mobilschool.fintrack.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.mobilschool.fintrack.data.source.local.entity.Wallet

class WalletFull(
    @Embedded
    val wallet: Wallet,

    @ColumnInfo(name = "currency_symbol")
    val currencySymbol: String

)