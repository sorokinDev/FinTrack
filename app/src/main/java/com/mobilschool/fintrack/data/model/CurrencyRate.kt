package com.mobilschool.fintrack.data.model

data class CurrencyRate(
        val date: String,
        val base: String,
        val rates: Map<String, Double>
)