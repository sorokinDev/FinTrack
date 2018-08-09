package com.mobilschool.fintrack.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchange_rates")
data class ExchangeRate (

        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "id")
        val id: String,

        @ColumnInfo(name = "rate")
        val rate: Double,

        @ColumnInfo(name = "date")
        val date: Long


)