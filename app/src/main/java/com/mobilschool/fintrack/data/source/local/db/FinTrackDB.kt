package com.mobilschool.fintrack.data.source.local.db

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.data.source.local.dao.*
import com.mobilschool.fintrack.data.source.local.entity.*
import java.util.concurrent.Executors

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

fun ioThread(f: () -> Unit){
    IO_EXECUTOR.execute(f)
}

@Database(entities = [(Currency::class), (ExchangeRate::class), (Wallet::class), (Transaction::class),
                        (Category::class), (Template::class)], version = 1)
@TypeConverters(TransactionTypeConverter::class, WalletTypeConverter::class)
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
                                            Currency("RUB", "\u20BD", true),
                                            Currency("USD", "$", true),
                                            Currency("EUR", "€", true)
                                    )

                                    roomDB.currencyDao().insertOrUpdateAll(defaultCurrenies)

                                    val defaultCategories = listOf(
                                            Category(0, context.resources.getString(R.string.job), TransactionType.INCOME, 7),
                                            Category(0, context.resources.getString(R.string.freelance), TransactionType.INCOME, 8),
                                            Category(0, context.resources.getString(R.string.shopping), TransactionType.EXPENSE, 6),
                                            Category(0, context.resources.getString(R.string.medicine), TransactionType.EXPENSE, 5),
                                            Category(0, context.resources.getString(R.string.car), TransactionType.EXPENSE, 1),
                                            Category(0, context.resources.getString(R.string.house), TransactionType.EXPENSE, 4),
                                            Category(0, context.resources.getString(R.string.clothes), TransactionType.EXPENSE, 2),
                                            Category(0, context.resources.getString(R.string.gifts), TransactionType.EXPENSE, 3)
                                    )

                                    roomDB.categoryDao().insertOrUpdateAll(defaultCategories)

                                    val defaultWallets = listOf(
                                            Wallet(0, "RUB", 0.0, context.resources.getString(R.string.cash), WalletType.CASH),
                                            Wallet(0, "RUB", 0.0, context.resources.getString(R.string.card), WalletType.CARD),
                                            Wallet(0, "RUB", 0.0, context.resources.getString(R.string.bank_account), WalletType.BANK_ACCOUNT)
                                    )

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
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao
    abstract fun periodicTransactionDao(): TemplateDao

}