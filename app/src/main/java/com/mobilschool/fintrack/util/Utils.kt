package com.mobilschool.fintrack.util

import com.mobilschool.fincalc.Record
import com.mobilschool.fincalc.entity.currency.Currency
import com.mobilschool.fincalc.entity.currency.RUB
import com.mobilschool.fincalc.entity.currency.USD
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun formatDate(date: Date): String =
            SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT).format(date)
    


    fun convertToCurrency(name: String, amount: Double): Currency {
        return when (name) {
            "USD" -> USD(amount)
            "RUB" -> RUB(amount)
            else -> {
                throw NotImplementedError()
            }
        }
    }
}


