package com.mobilschool.fintrack.data.source.local.converter

import androidx.room.TypeConverter
import com.mobilschool.fintrack.util.CurrencyPair

class CurrencyPairConverter {

    @TypeConverter
    fun currencyPairToString(currencyPair: CurrencyPair): String = "${currencyPair.first}_${currencyPair.second}"

    @TypeConverter
    fun stringToCurrencyPair(currencies: String): CurrencyPair{
        val curSpl = currencies.split("_").take(2)
        return Pair(curSpl[0], curSpl[1])
    }


}