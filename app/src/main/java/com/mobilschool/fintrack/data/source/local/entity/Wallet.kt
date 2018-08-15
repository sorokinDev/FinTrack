package com.mobilschool.fintrack.data.source.local.entity

import androidx.room.*
import com.mobilschool.fintrack.R

enum class WalletType{
    CASH, CARD, BANK_ACCOUNT
}


class WalletTypeConverter{
    companion object {
        val intConvertionMap = listOf(
                Pair(WalletType.CASH, 1),
                Pair(WalletType.CARD, 2),
                Pair(WalletType.BANK_ACCOUNT, 3)
        )

        val drawableConvertionMap = listOf(
                Pair(WalletType.CASH, R.drawable.ic_wallet),
                Pair(WalletType.CARD, R.drawable.ic_card),
                Pair(WalletType.BANK_ACCOUNT, R.drawable.ic_safebox)
        )

        fun walletTypeToDrawableRes(walletType: WalletType): Int {
            return drawableConvertionMap.first { it.first == walletType }.second
        }
    }


    @TypeConverter
    fun walletTypeToInt(type: WalletType): Int = intConvertionMap.first{ it.first == type }.second

    @TypeConverter
    fun intToWalletType(typeId: Int): WalletType = intConvertionMap.first{ it.second == typeId }.first

}

@Entity(tableName = "wallets", foreignKeys = [
    (ForeignKey(entity = Currency::class, parentColumns = ["id"], childColumns = ["currency_id"]))
], indices = [
    (Index(value = ["currency_id"], name="wallet_currency_id"))
])
class Wallet(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int = 0,

        @ColumnInfo(name = "currency_id")
        val currencyId: String,

        @ColumnInfo(name = "balance")
        val balance: Double,

        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "type")
        val type: WalletType

)