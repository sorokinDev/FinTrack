package com.mobilschool.fintrack.util

import com.mobilschool.fincalc.Record
import com.mobilschool.fincalc.entity.currency.Currency
import com.mobilschool.fincalc.entity.currency.RUB
import com.mobilschool.fincalc.entity.currency.USD
import com.mobilschool.fintrack.data.entity.Transaction
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun formatDate(date: Date): String =
            SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT).format(date)

    fun convertToRecords(transactions: List<Transaction>) =
            transactions.map {
                val money = convertToCurrency(it.currencyName, it.amount.toDouble())
                Record(it.type, money)
            }.toList()


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


