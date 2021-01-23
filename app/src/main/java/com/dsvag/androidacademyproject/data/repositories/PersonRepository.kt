package com.dsvag.androidacademyproject.data.repositories

import com.dsvag.androidacademyproject.BuildConfig
import com.dsvag.androidacademyproject.data.remote.ApiPersonService
import com.dsvag.androidacademyproject.models.moviecredits.MovieCredits
import com.dsvag.androidacademyproject.models.person.Person
import javax.inject.Inject

class PersonRepository @Inject constructor(
    private val apiPersonService: ApiPersonService
) {
    suspend fun getPerson(personId: Int): Person? {
        return apiPersonService.getPerson(personId, API_KEY).body()
    }

    suspend fun getPersonMovies(personId: Int): MovieCredits? {
        return apiPersonService.getPersonMovies(personId, API_KEY).body()
    }

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
    }

}