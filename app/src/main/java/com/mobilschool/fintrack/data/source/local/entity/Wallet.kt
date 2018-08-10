package com.mobilschool.fintrack.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class WalletType{
        CASH, CARD, BANK_ACCOUNT
}

@Entity(tableName = "wallets")
data class Wallet(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name="id")
        var id: Int = 0,

        @ColumnInfo(name="name")
        var name: String,

        @ColumnInfo(name="currency")
        var currency: String,

        @ColumnInfo(name="balance")
        var balance: Double,

        @ColumnInfo(name="wallet_type")
        var walletType: WalletType
)