package com.dsvag.androidacademyproject.data.repositories

import com.dsvag.androidacademyproject.BuildConfig
import com.dsvag.androidacademyproject.data.remote.ApiMovieService
import com.dsvag.androidacademyproject.models.credits.Credits
import com.dsvag.androidacademyproject.models.movie.Movie
import com.dsvag.androidacademyproject.models.movies.Request
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiMovieService: ApiMovieService
) {
    suspend fun getNowPlaying(page: Int): Request? {
        return apiMovieService.getNowPlaying(API_KEY, page).body()
    }

    suspend fun getPopular(page: Int): Request? {
        return apiMovieService.getPopular(API_KEY, page).body()
    }

    suspend fun getTopRated(page: Int): Request? {
        return apiMovieService.getTopRated(API_KEY, page).body()
    }

    suspend fun getMovie(movieId: Int): Movie? {
        return apiMovieService.getMovie(movieId, API_KEY).body()
    }

    suspend fun getCredits(movieId: Int): Credits? {
        return apiMovieService.getCredits(movieId, API_KEY).body()
    }

    suspend fun search(query: String, page: Int): Request? {
        return apiMovieService.search(API_KEY, query, page).body()
    }

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
    }
}