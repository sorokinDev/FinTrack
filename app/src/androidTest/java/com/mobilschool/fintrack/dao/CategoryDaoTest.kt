package com.mobilschool.fintrack.dao

import com.mobilschool.fintrack.LiveDataTestUtil
import com.mobilschool.fintrack.data.source.local.dao.CategoryDao
import com.mobilschool.fintrack.data.source.local.entity.Category
import com.mobilschool.fintrack.data.source.local.entity.TransactionType
import org.junit.Assert
import org.junit.Test

class CategoryDaoTest : BaseDaoTest() {

    companion object {
        val categories = listOf(
                Category(1, "cat1", TransactionType.INCOME, 0),
                Category(2, "cat2", TransactionType.EXPENSE, 0),
                Category(3, "cat3", TransactionType.INCOME, 0),
                Category(4, "cat4", TransactionType.EXPENSE, 0),
                Category(5, "cat5", TransactionType.EXPENSE, 0)
        )
    }

    lateinit var categoryDao: CategoryDao

    override fun initDao() {
        categoryDao = db.categoryDao()
        categoryDao.insertOrUpdateAll(categories)
    }

    @Test
    fun getCategoriesByType_Correct() {

        val incomeCategories = LiveDataTestUtil.getValue(categoryDao.getCategoriesByType(TransactionType.INCOME))
        val expenseCategories = LiveDataTestUtil.getValue(categoryDao.getCategoriesByType(TransactionType.EXPENSE))

        Assert.assertEquals(incomeCategories.size, 2)
        Assert.assertEquals(expenseCategories.size, 3)
    }

    @Test
    fun getCategoryById_Correct() {

        categoryDao.getCategoryById(2)

        val cat2 = LiveDataTestUtil.getValue(categoryDao.getCategoryById(2))
        val nocat = LiveDataTestUtil.getValue(categoryDao.getCategoryById(6))

        Assert.assertEquals(cat2[0].name, "cat2")
        Assert.assertEquals(nocat.size, 0)
    }

}