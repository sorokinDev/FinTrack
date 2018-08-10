package com.mobilschool.fintrack.data.source.local.dao

import androidx.room.*

@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateAll(obj: List<T>)

    @Update
    fun update(obj: T)

    @Update
    fun updateAll(obj: List<T>)

    @Delete
    fun delete(obj: T)

    @Delete
    fun deleteAll(obj: List<T>)

}