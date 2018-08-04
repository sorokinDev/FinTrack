package com.mobilschool.fintrack.di.component

import android.app.Application
import com.mobilschool.fintrack.FinTrackerApplication
import com.mobilschool.fintrack.di.module.AppModule
import com.mobilschool.fintrack.di.module.DBModule
import com.mobilschool.fintrack.di.module.NetworkModule
import com.mobilschool.fintrack.di.module.ViewModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AppModule::class,
            NetworkModule::class,
            DBModule::class,
            ViewModule::class
        ]
)
interface AppComponent: AndroidInjector<FinTrackerApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(app: Application):Builder

        fun build(): AppComponent
    }
}