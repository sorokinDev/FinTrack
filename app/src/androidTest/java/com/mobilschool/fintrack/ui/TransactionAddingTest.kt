package com.mobilschool.fintrack.ui

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.rule.ActivityTestRule
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test
import android.widget.TextView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matcher


class TransactionAddingTest {

    @get:Rule
    var activityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun validateTransactionIncomeAdd() {
        onView(withId(R.id.btn_new_transaction)).perform(click())
        onView(withId(R.id.rb_income)).perform(click())
        onView(withId(R.id.et_sum)).perform(typeText("123"), closeSoftKeyboard())
        onView(withId(R.id.btn_confirm_new_transaction)).perform(click())
    }

    @Test
    fun validateTransactionAdd() {
        onView(withId(R.id.btn_new_transaction)).perform(click())
        onView(withId(R.id.rb_expense)).perform(click())
        onView(withId(R.id.et_sum)).perform(typeText("123"), closeSoftKeyboard())
        onView(withId(R.id.btn_confirm_new_transaction)).perform(click())
    }

}