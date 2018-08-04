package com.mobilschool.fintrack.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "wallets")
data class Wallet(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name="id")
        var id: Int = 0,

        @ColumnInfo(name="name")
        var name: String,

        @ColumnInfo(name="currency")
        var currency:String
)