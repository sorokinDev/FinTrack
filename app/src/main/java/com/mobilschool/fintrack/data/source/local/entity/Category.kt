package com.mobilschool.fintrack.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mobilschool.fintrack.R

object CategoryImgResConverter{

    val convertionMap = listOf(
            Pair(1, R.drawable.car),
            Pair(2, R.drawable.clothes),
            Pair(3, R.drawable.gift),
            Pair(4, R.drawable.home),
            Pair(5, R.drawable.medicine),
            Pair(6, R.drawable.product),
            Pair(7, R.drawable.job),
            Pair(8, R.drawable.job)
    )

    fun getDrawable(res: Int): Int = convertionMap.firstOrNull{ it.first == res }?.second ?: R.drawable.product

}

@Entity(tableName = "categories")
class Category(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int = 0,

        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "type")
        val type: TransactionType,

        @ColumnInfo(name = "img_res")
        val imgRes: Int

)