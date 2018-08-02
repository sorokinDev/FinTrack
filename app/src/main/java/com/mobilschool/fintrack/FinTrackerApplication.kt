package com.mobilschool.fintrack

import android.app.Application
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.*
import android.preference.PreferenceManager
import com.mobilschool.fincalc.TypeOperation
import com.mobilschool.fintrack.data.model.Account
import com.mobilschool.fintrack.data.model.Transaction
import com.mobilschool.fintrack.di.component.DaggerAppComponent
import com.mobilschool.fintrack.util.Utils
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class FinTrackerApplication : DaggerApplication() {

    companion object {
        val BASE_CURRENCY = "RUB"
        var CURRENT_ACCOUNT = "Кредитная карта"
        val mockAccounts = listOf(Account("Кредитная карта", BASE_CURRENCY, listOf(
                Transaction("500", "USD", Utils.formatDate(Calendar.getInstance().time), "Зарплата", TypeOperation.INCOME),
                Transaction("5000", "RUB", Utils.formatDate(Calendar.getInstance().time), "Продукты", TypeOperation.OUTCOME),
                Transaction("700", "RUB", Utils.formatDate(Calendar.getInstance().time), "Одежда", TypeOperation.OUTCOME)
        )),
                Account("Наличные", BASE_CURRENCY, listOf(
                        Transaction("300", "USD", Utils.formatDate(Calendar.getInstance().time), "Зарплата", TypeOperation.INCOME),
                        Transaction("5000", "RUB", Utils.formatDate(Calendar.getInstance().time), "Продукты", TypeOperation.OUTCOME),
                        Transaction("700", "RUB", Utils.formatDate(Calendar.getInstance().time), "Одежда", TypeOperation.OUTCOME)
                )))
    }

    private var preferences: SharedPreferences? = null
    private var locale: Locale? = null
    private var lang: String? = null


    override fun onCreate() {
        super.onCreate()
        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        lang = preferences?.getString("lang", "default")
        if (lang.equals("default")) {
            lang = resources.configuration.locale.country
        }
        locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, null)
    }


    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, null)
    }
    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
            = DaggerAppComponent.builder().create(this).build()
}