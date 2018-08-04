package com.mobilschool.fintrack.data.source.local.entity

import androidx.room.*
import com.mobilschool.fintrack.data.source.local.converter.CurrencyPairConverter
import com.mobilschool.fintrack.data.source.local.converter.DateConverter
import com.mobilschool.fintrack.util.CurrencyPair
import java.util.*


@Entity(tableName = "exchange_rates")
data class LocalExchangeRate(

        @PrimaryKey
        @ColumnInfo(name = "currencies")
        val currencies: CurrencyPair,

        @ColumnInfo(name = "rate")
        var rate: Double,

        @ColumnInfo(name = "date")
        var date: Date
)