package com.mobilschool.fintrack.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "money_currencies")
data class MoneyCurrency (

        @PrimaryKey
        @ColumnInfo(name = "code")
        val code: String,

        @ColumnInfo(name = "symbol")
        val symbol: String,

        @ColumnInfo(name = "is_favorite")
        var isFavorite: Boolean

)