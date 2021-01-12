package com.dsvag.androidacademyproject.data.repositories

import android.content.Context
import com.dsvag.androidacademyproject.utils.loadMovies
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    suspend fun fetchMovies() = withContext(Dispatchers.IO) { loadMovies(context) }
}