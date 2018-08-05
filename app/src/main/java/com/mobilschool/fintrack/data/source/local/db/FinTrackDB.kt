package com.mobilschool.fintrack.data.source.local.db

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mobilschool.fintrack.data.source.local.converter.CurrencyPairConverter
import com.mobilschool.fintrack.data.source.local.converter.DateConverter
import com.mobilschool.fintrack.data.source.local.dao.CurrencyDao
import com.mobilschool.fintrack.data.source.local.dao.ExchangeDao
import com.mobilschool.fintrack.data.source.local.dao.WalletDao
import com.mobilschool.fintrack.data.source.local.entity.LocalExchangeRate
import com.mobilschool.fintrack.data.source.local.entity.MoneyCurrency
import com.mobilschool.fintrack.data.source.local.entity.Wallet
import java.util.concurrent.Executors

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

fun ioThread(f: () -> Unit){
    IO_EXECUTOR.execute(f)
}

@Database(entities = [(MoneyCurrency::class), (LocalExchangeRate::class), (Wallet::class)], version = 1)
@TypeConverters(CurrencyPairConverter::class, DateConverter::class)
abstract class FinTrackDB : RoomDatabase() {

    companion object {
        val FILENAME = "FinTrack.db"

        @Volatile private var INSTANCE: FinTrackDB? = null

        fun getInstance(context: Context): FinTrackDB = INSTANCE ?: synchronized(this){
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext, FinTrackDB::class.java, FinTrackDB.FILENAME)
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                ioThread {
                                    val roomDB = getInstance(context)

                                    val defaultCurrenies = listOf(
                                            MoneyCurrency("RUB", "Р", true),
                                            MoneyCurrency("USD", "$", true),
                                            MoneyCurrency("EUR", "E", true)
                                    )

                                    val defaultWallets = listOf(
                                            Wallet(0, "Cash", "RUB", 1000.0),
                                            Wallet(0, "Credit card", "USD", 45.0)
                                    )

                                    roomDB.currencyDao().insertOrUpdateAll(defaultCurrenies)
                                    roomDB.walletDao().insertOrUpdateAll(defaultWallets)

                                }
                            }

                            override fun onOpen(db: SupportSQLiteDatabase) {
                                super.onOpen(db)
                            }

                        })
                        .build()

    }



    abstract fun currencyDao(): CurrencyDao
    abstract fun exchangeDao(): ExchangeDao
    abstract fun walletDao(): WalletDao

}