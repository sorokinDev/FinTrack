package com.mobilschool.fintrack.data.source.local.db

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.data.source.local.converter.CurrencyPairConverter
import com.mobilschool.fintrack.data.source.local.converter.DateConverter
import com.mobilschool.fintrack.data.source.local.converter.TransactionTypeConverter
import com.mobilschool.fintrack.data.source.local.converter.WalletTypeConverter
import com.mobilschool.fintrack.data.source.local.dao.*
import com.mobilschool.fintrack.data.source.local.entity.*
import java.util.concurrent.Executors

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

fun ioThread(f: () -> Unit){
    IO_EXECUTOR.execute(f)
}

@Database(entities = [(MoneyCurrency::class), (LocalExchangeRate::class), (Wallet::class), (MoneyTransaction::class),
                        (TransactionCategory::class)], version = 1)
@TypeConverters(CurrencyPairConverter::class, DateConverter::class, TransactionTypeConverter::class, WalletTypeConverter::class)
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
                                            Wallet(0, context.resources.getString(R.string.cash), "RUB", 0.0, WalletType.CASH),
                                            Wallet(0, context.resources.getString(R.string.card), "RUB", 0.0, WalletType.CARD),
                                            Wallet(0, context.resources.getString(R.string.bank_account), "RUB", 0.0, WalletType.BANK_ACCOUNT)
                                    )

                                    val defaultCategories = listOf(
                                            TransactionCategory(0, context.resources.getString(R.string.job), TransactionType.INCOME),
                                            TransactionCategory(0, context.resources.getString(R.string.freelance), TransactionType.INCOME),
                                            TransactionCategory(0, context.resources.getString(R.string.tutoring), TransactionType.INCOME),
                                            TransactionCategory(0, context.resources.getString(R.string.debt_return), TransactionType.INCOME),
                                            TransactionCategory(0, context.resources.getString(R.string.shopping), TransactionType.EXPENSE),
                                            TransactionCategory(0, context.resources.getString(R.string.medicine), TransactionType.EXPENSE),
                                            TransactionCategory(0, context.resources.getString(R.string.car), TransactionType.EXPENSE),
                                            TransactionCategory(0, context.resources.getString(R.string.house), TransactionType.EXPENSE),
                                            TransactionCategory(0, context.resources.getString(R.string.hobby), TransactionType.EXPENSE),
                                            TransactionCategory(0, context.resources.getString(R.string.clothes), TransactionType.EXPENSE)
                                    )

                                    roomDB.currencyDao().insertOrUpdateAll(defaultCurrenies)
                                    roomDB.walletDao().insertOrUpdateAll(defaultWallets)
                                    roomDB.categoryDao().insertOrUpdateAll(defaultCategories)

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
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao

}