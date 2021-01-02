package com.dsvag.androidacademyproject.data.di

import android.app.Application
import android.content.Context

class App : Application() {
    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        component = AppComponent(this)
    }
}

fun Context.getAppComponent(): AppComponent = (applicationContext as App).component