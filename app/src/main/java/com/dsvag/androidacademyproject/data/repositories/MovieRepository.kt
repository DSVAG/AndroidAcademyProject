package com.dsvag.androidacademyproject.data.repositories

import com.dsvag.androidacademyproject.BuildConfig
import com.dsvag.androidacademyproject.data.local.MovieDao
import com.dsvag.androidacademyproject.data.remote.ApiMovieService
import com.dsvag.androidacademyproject.models.credits.Credits
import com.dsvag.androidacademyproject.models.movies.Movie
import com.dsvag.androidacademyproject.models.movies.Request
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiMovieService: ApiMovieService,
    private val movieDao: MovieDao,
) {
    suspend fun getNowPlaying(page: Int): Request {
        return apiMovieService.getNowPlaying(API_KEY, page)
    }

    suspend fun getPopular(page: Int): Request {
        return apiMovieService.getPopular(API_KEY, page)
    }

    suspend fun getTopRated(page: Int): Request {
        return apiMovieService.getTopRated(API_KEY, page)
    }

    suspend fun getMovie(movieId: Int): Movie {
        return apiMovieService.getMovie(movieId, API_KEY)
    }

    suspend fun getMovieCredits(movieId: Int): Credits {
        return apiMovieService.getCredits(movieId, API_KEY)
    }

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
    }
}