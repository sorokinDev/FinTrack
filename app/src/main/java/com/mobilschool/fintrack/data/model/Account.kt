package com.mobilschool.fintrack.data.model

data class Account(
        val uniqueName:String,
        val baseCurrency:String,
        val transactions:List<Transaction>
)

