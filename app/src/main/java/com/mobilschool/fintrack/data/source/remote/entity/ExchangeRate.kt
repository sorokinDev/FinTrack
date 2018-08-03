package com.mobilschool.fintrack.data.source.remote.entity

import java.util.*

data class ExchangeRate(
        val date: Date,
        val base: String,
        val rates: Map<String, Double>
)