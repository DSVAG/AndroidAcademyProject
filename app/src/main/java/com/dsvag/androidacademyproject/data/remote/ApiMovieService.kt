package com.dsvag.androidacademyproject.data.remote

import com.dsvag.androidacademyproject.models.credits.PersonsResponse
import com.dsvag.androidacademyproject.models.movie.Movie
import com.dsvag.androidacademyproject.models.movie.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiMovieService {
    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("page") page: Int,
    ): MovieResponse

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("page") page: Int,
    ): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("page") page: Int,
    ): MovieResponse

    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Long,
    ): Movie

    @GET("movie/{id}/credits")
    suspend fun getCredits(
        @Path("id") id: Long,
    ): PersonsResponse
}