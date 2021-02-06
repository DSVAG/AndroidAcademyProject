package com.dsvag.androidacademyproject.data.repositories

import com.dsvag.androidacademyproject.BuildConfig
import com.dsvag.androidacademyproject.data.remote.ApiMovieService
import com.dsvag.androidacademyproject.models.credits.Credits
import com.dsvag.androidacademyproject.models.movies.Movie
import com.dsvag.androidacademyproject.models.movies.Request
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiMovieService: ApiMovieService
) {
    suspend fun getNowPlaying(page: Int): Response<Request> {
        return apiMovieService.getNowPlaying(API_KEY, page)
    }

    suspend fun getPopular(page: Int): Response<Request> {
        return apiMovieService.getPopular(API_KEY, page)
    }

    suspend fun getTopRated(page: Int): Response<Request> {
        return apiMovieService.getTopRated(API_KEY, page)
    }

    suspend fun getMovie(movieId: Int): Response<Movie> {
        return apiMovieService.getMovie(movieId, API_KEY)
    }

    suspend fun getCredits(movieId: Int): Response<Credits> {
        return apiMovieService.getCredits(movieId, API_KEY)
    }

    suspend fun search(query: String, page: Int): Response<Request> {
        return apiMovieService.search(API_KEY, query, page)
    }

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
    }
}