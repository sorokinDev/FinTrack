package com.mobilschool.fintrack.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mobilschool.fintrack.data.source.local.entity.TransactionCategory
import com.mobilschool.fintrack.data.source.local.entity.TransactionType

@Dao
interface CategoryDao: BaseDao<TransactionCategory> {
    @Query("SELECT * FROM transaction_categories WHERE transaction_type = :type")
    fun getCategoriesByType(type: TransactionType): LiveData<List<TransactionCategory>>

}