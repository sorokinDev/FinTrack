package com.mobilschool.fintrack.dao

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4


import org.junit.runner.RunWith

import androidx.room.Room
import com.mobilschool.fintrack.data.source.local.db.FinTrackDB
import org.junit.After
import org.junit.Before
import java.io.IOException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule




@RunWith(AndroidJUnit4::class)
abstract class BaseDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var db: FinTrackDB


    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, FinTrackDB::class.java).allowMainThreadQueries().build()
        initDao()
    }

    abstract fun initDao()

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


}