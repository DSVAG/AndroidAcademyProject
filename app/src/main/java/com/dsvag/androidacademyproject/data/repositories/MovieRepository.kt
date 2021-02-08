package com.dsvag.androidacademyproject.data.repositories

import com.dsvag.androidacademyproject.BuildConfig
import com.dsvag.androidacademyproject.data.local.MovieDao
import com.dsvag.androidacademyproject.data.remote.ApiMovieService
import com.dsvag.androidacademyproject.models.credits.Credits
import com.dsvag.androidacademyproject.models.movie.Movie
import com.dsvag.androidacademyproject.models.movie.Request
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

    suspend fun getMovie(movieId: Long): Movie? {
        var movie = runCatching { movieDao.getMovieById(movieId) }.getOrNull()

        if (movie == null) {
            movie = runCatching { apiMovieService.getMovie(movieId, API_KEY) }.getOrNull()

            if (movie != null) {
                movieDao.insert(movie)
            }
        }

        return movie
    }

    suspend fun getMovieCredits(movieId: Long): Credits {
        return apiMovieService.getCredits(movieId, API_KEY)
    }

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
    }
}