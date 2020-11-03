package com.ducnv.moviehunt

import android.app.Application
import com.ducnv.moviehunt.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import androidx.multidex.MultiDex

class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        startKoin {
            androidContext(this@MyApplication)
            modules(appModules)
        }
    }
}