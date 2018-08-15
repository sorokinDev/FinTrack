package com.mobilschool.fintrack.data.source.local.entity

import androidx.room.*


enum class TransactionType{
    EXPENSE, INCOME
}

class TransactionTypeConverter{
    companion object {
        val convertionMap = listOf(
                Pair(TransactionType.EXPENSE, 1),
                Pair(TransactionType.INCOME, 2)
        )
    }


    @TypeConverter
    fun transactionTypeToInt(type: TransactionType): Int = convertionMap.first{ it.first == type }.second

    @TypeConverter
    fun intToTransactionType(typeId: Int): TransactionType = convertionMap.first{ it.second == typeId }.first

}


@Entity(tableName = "transactions", foreignKeys = [
    (ForeignKey(entity = Currency::class, parentColumns = ["id"], childColumns = ["currency_id"])),
    (ForeignKey(entity = Wallet::class, parentColumns = ["id"], childColumns = ["wallet_id"])),
    (ForeignKey(entity = Category::class, parentColumns = ["id"], childColumns = ["category_id"]))
], indices = [
    (Index(value = ["currency_id"], name="transaction_currency_id")),
    (Index(value = ["wallet_id"], name="transaction_wallet_id")),
    (Index(value = ["category_id"], name="transaction_category_id"))])
class Transaction (

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int = 0,

        @ColumnInfo(name = "currency_id")
        val currencyId: String,

        @ColumnInfo(name = "wallet_id")
        val walletId: Int,

        @ColumnInfo(name = "date")
        val date: Long,

        @ColumnInfo(name = "amount")
        val amount: Double,

        @ColumnInfo(name = "category_id")
        val categoryId: Int,

        @ColumnInfo(name = "type")
        val type: TransactionType

)