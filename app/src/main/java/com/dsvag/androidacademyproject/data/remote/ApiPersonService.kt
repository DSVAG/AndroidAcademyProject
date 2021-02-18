package com.dsvag.androidacademyproject.data.remote

import com.dsvag.androidacademyproject.models.person.Person
import com.dsvag.androidacademyproject.models.person.PersonMovies
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiPersonService {
    @GET("person/{personId}")
    suspend fun getPerson(
        @Path("personId") personId: Long,
    ): Person

    @GET("person/{personId}/movie_credits")
    suspend fun getPersonMovies(
        @Path("personId") personId: Long,
    ): PersonMovies
}