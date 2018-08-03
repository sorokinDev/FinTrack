package com.mobilschool.fintrack

import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.*
import android.preference.PreferenceManager
import com.mobilschool.fincalc.TypeOperation
import com.mobilschool.fintrack.di.component.DaggerAppComponent
import com.mobilschool.fintrack.util.Utils
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class FinTrackerApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
            = DaggerAppComponent.builder().create(this).build()
}