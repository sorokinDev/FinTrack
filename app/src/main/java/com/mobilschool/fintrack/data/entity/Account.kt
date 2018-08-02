package com.mobilschool.fintrack.data.entity

data class Account(
        val uniqueName:String,
        val baseCurrency:String,
        val transactions:List<Transaction>
)

