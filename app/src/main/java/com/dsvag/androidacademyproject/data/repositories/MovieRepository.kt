package com.dsvag.androidacademyproject.data.repositories

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
        return apiMovieService.getNowPlaying(page)
    }

    suspend fun getPopular(page: Int): MovieResponse {
        return apiMovieService.getPopular(page)
    }

    suspend fun getTopRated(page: Int): MovieResponse {
        return apiMovieService.getTopRated(page)
    }

    suspend fun getMovie(movieId: Long): Movie {
        var movie = movieDao.getMovieById(movieId)

        if (movie != null) {
            return movie
        }

        movie = apiMovieService.getMovie(movieId)

        val personsIds = apiMovieService.getCredits(movieId).cast.map { it.id }

        movie = movie.copy(castIds = personsIds)

        movieDao.insert(movie)

        return movie
    }

    suspend fun getMovieCredits(movieId: Long): List<Cast> {
        return apiMovieService.getCredits(movieId).cast
    }

    suspend fun updateCache() {
        val listIds = movieDao.getMoviesIds()

        listIds.forEach { id ->
            val newMovie = apiMovieService.getMovie(id)
            movieDao.insert(newMovie)
        }
    }
}