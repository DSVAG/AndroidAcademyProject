package com.dsvag.androidacademyproject.data.repositories

import com.dsvag.androidacademyproject.BuildConfig
import com.dsvag.androidacademyproject.data.local.MovieDao
import com.dsvag.androidacademyproject.data.remote.ApiMovieService
import com.dsvag.androidacademyproject.models.credits.Cast
import com.dsvag.androidacademyproject.models.movie.Movie
import com.dsvag.androidacademyproject.models.movie.MovieResponse
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiMovieService: ApiMovieService,
    private val movieDao: MovieDao,
) {
    suspend fun getNowPlaying(page: Int): MovieResponse {
        return apiMovieService.getNowPlaying(API_KEY, page)
    }

    suspend fun getPopular(page: Int): MovieResponse {
        return apiMovieService.getPopular(API_KEY, page)
    }

    suspend fun getTopRated(page: Int): MovieResponse {
        return apiMovieService.getTopRated(API_KEY, page)
    }

    suspend fun getMovie(movieId: Long): Movie {
        var movie = movieDao.getMovieById(movieId)

        if (movie != null) {
            return movie
        }

        movie = apiMovieService.getMovie(movieId, API_KEY)

        val personsIds = apiMovieService.getCredits(movieId, API_KEY).cast.map { it.id }

        movie = movie.copy(castIds = personsIds)

        movieDao.insert(movie)

        return movie
    }

    suspend fun getMovieCredits(movieId: Long): List<Cast> {
        return apiMovieService.getCredits(movieId, API_KEY).cast
    }

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
    }
}