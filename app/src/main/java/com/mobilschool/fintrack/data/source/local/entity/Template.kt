package com.mobilschool.fintrack.data.source.local.entity

import androidx.room.*


@Entity(tableName = "templates", foreignKeys = [
    (ForeignKey(entity = Currency::class, parentColumns = ["id"], childColumns = ["currency_id"])),
    (ForeignKey(entity = Wallet::class, parentColumns = ["id"], childColumns = ["wallet_id"])),
    (ForeignKey(entity = Category::class, parentColumns = ["id"], childColumns = ["category_id"]))
], indices = [
    (Index(value = ["currency_id"], name = "template_currency_id")),
    (Index(value = ["wallet_id"], name = "template_wallet_id")),
    (Index(value = ["category_id"], name = "template_category_id"))])
class Template (

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
        val type: TransactionType,

        @ColumnInfo(name = "period")
        val period: Long

)