package com.mobilschool.fintrack.util

import android.content.Context
import android.net.ConnectivityManager
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

fun Date.diff(date1: Date): Long{
    return this.time - date1.time
}


typealias CurrencyPair = Pair<String, String>

fun Context.isNetworkStatusAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    connectivityManager?.let {
        it.activeNetworkInfo?.let {
            if (it.isConnected) return true
        }
    }
    return false
}
