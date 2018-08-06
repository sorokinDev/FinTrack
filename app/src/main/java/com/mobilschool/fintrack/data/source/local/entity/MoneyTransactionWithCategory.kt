package com.mobilschool.fintrack.data.source.local.entity

import java.util.*

class MoneyTransactionWithCategory(
        id: Int, walletId: Int, amount: Double, date: Date, type: TransactionType,
        category: Int, val categoryName: String
) : MoneyTransaction(id, walletId, amount, date, type, category)