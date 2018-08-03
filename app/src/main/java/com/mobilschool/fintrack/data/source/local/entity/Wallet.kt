package com.mobilschool.fintrack.data.source.local.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
class Wallet (
        @Id var id: Long = 0,
        var name: String,
        var balance: Double
){
    lateinit var currency: ToOne<MoneyCurrency>
}