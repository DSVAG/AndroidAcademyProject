package com.dsvag.androidacademyproject.data.remote

import com.dsvag.androidacademyproject.models.person.Person
import com.dsvag.androidacademyproject.models.person.PersonMovies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiPersonService {
    @GET("person/{personId}")
    suspend fun getPerson(
        @Path("personId") personId: Int,
        @Query("api_key") api_key: String,
    ): Person

    @GET("person/{personId}/movie_credits")
    suspend fun getPersonMovies(
        @Path("personId") personId: Int,
        @Query("api_key") api_key: String,
    ): PersonMovies
}