package com.mobilschool.fintrack.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

enum class TransactionType{
     EXPENSE, INCOME
}

// TODO: Remove categoryName and make another class for this purpose

@Entity(tableName = "money_transactions")
open class MoneyTransaction(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int = 0,

        @ColumnInfo(name = "walletId")
        val walletId: Int,

        @ColumnInfo(name = "amount")
        val amount: Double,

        @ColumnInfo(name = "date")
        val date: Date,

        @ColumnInfo(name = "transaction_type")
        val type: TransactionType,

        @ColumnInfo(name = "category_id")
        val category: Int
)

