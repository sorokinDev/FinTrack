package com.mobilschool.fintrack.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class Currency(

        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "id")
        val id: String,

        @ColumnInfo(name = "symbol")
        val symbol: String,

        @ColumnInfo(name = "is_favorite")
        val isFavorite: Boolean

)