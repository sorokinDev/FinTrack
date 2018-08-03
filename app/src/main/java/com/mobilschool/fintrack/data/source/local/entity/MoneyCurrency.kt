package com.mobilschool.fintrack.data.source.local.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class MoneyCurrency(@Id var id: Long = 0, val code: String, var isFavorite: Boolean)