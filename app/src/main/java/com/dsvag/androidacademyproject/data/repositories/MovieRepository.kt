package com.dsvag.androidacademyproject.data.repositories

import com.dsvag.androidacademyproject.data.local.MovieDao
import com.dsvag.androidacademyproject.data.local.PersonDao
import com.dsvag.androidacademyproject.data.remote.ApiMovieService
import com.dsvag.androidacademyproject.data.remote.ApiPersonService
import com.dsvag.androidacademyproject.models.movie.Movie
import com.dsvag.androidacademyproject.models.movie.MovieResponse
import com.dsvag.androidacademyproject.models.person.Person
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiMovieService: ApiMovieService,
    private val apiPersonService: ApiPersonService,
    private val movieDao: MovieDao,
    private val personDao: PersonDao,
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

        val castIds = apiMovieService.getCredits(movieId).cast.map { it.id }

        movie = movie.copy(castIds = castIds)

        movieDao.insert(movie)

        return movie
    }

    suspend fun getMovieCredits(movie: Movie): List<Person> {
        val persons = mutableListOf<Person>()

        val castIds = if (movie.castIds.size > 1) movie.castIds.toList()
        else apiMovieService.getCredits(movie.id).cast.map { it.id }

        movieDao.insert(movie.copy(castIds = castIds))

        castIds.forEach { id ->
            var person = personDao.getPersonById(id)

            if (person == null) {
                person = apiPersonService.getPerson(id)
                personDao.insert(person)
            }

            persons.add(person)
        }

        return persons
    }

    suspend fun updateCache() {
        val listIds = movieDao.getMoviesIds()

        listIds.forEach { id ->
            val newMovie = apiMovieService.getMovie(id)
            movieDao.insert(newMovie)
        }
    }
}