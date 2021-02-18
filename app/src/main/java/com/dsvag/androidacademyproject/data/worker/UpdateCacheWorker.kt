package com.dsvag.androidacademyproject.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.dsvag.androidacademyproject.data.repositories.MovieRepository
import com.dsvag.androidacademyproject.data.repositories.PersonRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

@HiltWorker
class UpdateCacheWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val personRepository: PersonRepository,
    private val movieRepository: MovieRepository,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            movieRepository.updateCache()
            personRepository.updateCache()

            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }

    companion object {
        private val constraints by lazy {
            Constraints
                .Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build()
        }

        val request by lazy {
            PeriodicWorkRequest
                .Builder(UpdateCacheWorker::class.java, 12, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()
        }
    }

}