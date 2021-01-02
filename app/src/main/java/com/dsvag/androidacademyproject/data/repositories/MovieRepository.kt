package com.dsvag.androidacademyproject.data.repositories

import android.content.Context
import com.dsvag.androidacademyproject.data.utils.loadMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val context: Context) {

    suspend fun fetchMovies() = withContext(Dispatchers.IO) { loadMovies(context) }

}