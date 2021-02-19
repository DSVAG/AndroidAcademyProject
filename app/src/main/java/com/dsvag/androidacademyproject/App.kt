package com.dsvag.androidacademyproject

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() = Configuration
        .Builder()
        .setWorkerFactory(workerFactory)
        .setMinimumLoggingLevel(Log.INFO)
        .build()

    override fun onCreate() {
        super.onCreate()

        //WorkManager.getInstance(this).enqueue(UpdateCacheWorker.request)
    }
}