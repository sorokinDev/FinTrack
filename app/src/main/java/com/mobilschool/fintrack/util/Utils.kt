package com.mobilschool.fintrack.util

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import timber.log.Timber
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt
import kotlin.math.roundToLong


fun Date.diff(date1: Date): Long{
    return this.time - date1.time
}


typealias CurrencyPair = Pair<String, String>
typealias CurrencyAmountPair = Pair<String, Double>

fun Context.isNetworkStatusAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    connectivityManager?.let {
        it.activeNetworkInfo?.let {
            if (it.isConnected) return true
        }
    }
    return false
}


fun <T> LiveData<T>.observe(owner: LifecycleOwner, notNull: (data : T) -> Unit,
                            isNull: () -> Unit = { Timber.d("Observer: Live data is null") }){
    this.observe(owner, androidx.lifecycle.Observer {
        if(it != null){
            notNull(it)
        }else{
            isNull()
        }
    })
}


fun Double.toMoney() = ((this * 100).roundToLong() / 100.0)

fun Double.toMoneyString() = DecimalFormat("#,###").format(this.roundToLong()).replace(',', ' ')