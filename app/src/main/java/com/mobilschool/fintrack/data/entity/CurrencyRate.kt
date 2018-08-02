package com.mobilschool.fintrack.data.entity

data class CurrencyRate(
        val date: String,
        val base: String,
        val rates: Map<String, Double>
)