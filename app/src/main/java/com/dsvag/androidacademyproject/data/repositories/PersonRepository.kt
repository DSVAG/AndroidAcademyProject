package com.dsvag.androidacademyproject.data.repositories

import com.dsvag.androidacademyproject.data.local.MovieDao
import com.dsvag.androidacademyproject.data.local.PersonDao
import com.dsvag.androidacademyproject.data.remote.ApiPersonService
import com.dsvag.androidacademyproject.models.movie.Movie
import com.dsvag.androidacademyproject.models.person.Person
import javax.inject.Inject

class PersonRepository @Inject constructor(
    private val apiPersonService: ApiPersonService,
    private val movieDao: MovieDao,
    private val personDao: PersonDao,
) {
    suspend fun getPersonWithMovies(personId: Long): Person {
        var person = personDao.getPersonById(personId)

        if (person != null) {
            return person
        }

        person = apiPersonService.getPerson(personId)
        val movies = apiPersonService.getPersonMovies(personId)

        movieDao.insertAll(movies.asCast)

        person = person.copy(moviesIds = movies.asCast.map { it.id })

        personDao.insert(person)

        return person
    }

    suspend fun getPersonMovies(moviesIds: List<Long>): List<Movie> {
        return movieDao.getMoviesByIds(moviesIds)
    }

    suspend fun updateCache() {
        val listIds = personDao.getPersonsIds()

        listIds.forEach { id ->
            val newPerson = apiPersonService.getPerson(id)
            personDao.insert(newPerson)
        }
    }
}