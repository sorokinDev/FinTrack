package com.mobilschool.fintrack.data.source.local.converter

import androidx.room.TypeConverter
import com.mobilschool.fintrack.data.source.local.entity.TransactionType
import com.mobilschool.fintrack.data.source.local.entity.WalletType

class WalletTypeConverter{
    companion object {
        val convertionMap = listOf(
                Pair(WalletType.CASH, 1),
                Pair(WalletType.CARD, 2),
                Pair(WalletType.BANK_ACCOUNT, 3)
        )
    }


    @TypeConverter
    fun walletTypeToInt(type: WalletType): Int = convertionMap.first{ it.first == type }.second

    @TypeConverter
    fun intToWalletType(typeId: Int): WalletType = convertionMap.first{ it.second == typeId }.first


}