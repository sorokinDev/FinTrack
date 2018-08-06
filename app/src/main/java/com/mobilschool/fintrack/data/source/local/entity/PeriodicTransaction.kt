package com.mobilschool.fintrack.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "periodic_transactions")
class PeriodicTransaction (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int = 0,

        @ColumnInfo(name = "walletId")
        val walletId: Int,

        @ColumnInfo(name = "amount")
        val amount: Double,

        @ColumnInfo(name = "last_execution")
        var lastExecution: Date,

        @ColumnInfo(name = "frequency")
        val frequency: Long,

        @ColumnInfo(name = "transaction_type")
        val type: TransactionType,

        @ColumnInfo(name = "category_id")
        val category: Int
)