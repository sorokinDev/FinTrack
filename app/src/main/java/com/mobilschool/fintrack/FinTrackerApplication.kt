package com.mobilschool.fintrack

import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.*
import android.preference.PreferenceManager
import com.mobilschool.fincalc.TypeOperation
import com.mobilschool.fintrack.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber


class FinTrackerApplication : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
            = DaggerAppComponent.builder().create(this).build()
}