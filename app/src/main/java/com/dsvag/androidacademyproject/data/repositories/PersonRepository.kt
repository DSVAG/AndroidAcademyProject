package com.dsvag.androidacademyproject.data.repositories

import com.dsvag.androidacademyproject.BuildConfig
import com.dsvag.androidacademyproject.data.local.PersonDao
import com.dsvag.androidacademyproject.data.remote.ApiPersonService
import com.dsvag.androidacademyproject.models.person.Person
import com.dsvag.androidacademyproject.models.person.PersonMovies
import javax.inject.Inject

class PersonRepository @Inject constructor(
    private val apiPersonService: ApiPersonService,
    private val personDao: PersonDao,
) {
    suspend fun getPerson(personId: Int): Person {
        return apiPersonService.getPerson(personId, API_KEY)
    }

    suspend fun getPersonMovies(personId: Int): PersonMovies {
        return apiPersonService.getPersonMovies(personId, API_KEY)
    }

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
    }
}