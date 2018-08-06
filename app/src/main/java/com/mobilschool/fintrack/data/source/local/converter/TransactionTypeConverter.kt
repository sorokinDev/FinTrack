package com.mobilschool.fintrack.data.source.local.converter

import androidx.room.TypeConverter
import com.mobilschool.fintrack.data.source.local.entity.TransactionType

class TransactionTypeConverter{
    companion object {
        val convertionMap = listOf(
                Pair(TransactionType.EXPENSE, 1),
                Pair(TransactionType.INCOME, 2)
        )
    }


    @TypeConverter
    fun transactionTypeToInt(type: TransactionType): Int = convertionMap.first{ it.first == type }.second

    @TypeConverter
    fun intToTransactionType(typeId: Int): TransactionType = convertionMap.first{ it.second == typeId }.first


}