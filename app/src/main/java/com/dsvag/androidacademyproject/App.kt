package com.dsvag.androidacademyproject

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.dsvag.androidacademyproject.data.worker.UpdateCacheWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        val configuration = Configuration
            .Builder()
            .setWorkerFactory(workerFactory)
            .build()

        WorkManager.initialize(this, configuration)

        return configuration
    }

    override fun onCreate() {
        super.onCreate()

        WorkManager.getInstance(this).enqueue(UpdateCacheWorker.request)
    }
}