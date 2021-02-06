package com.dsvag.androidacademyproject.data.remote

import com.dsvag.androidacademyproject.models.credits.Credits
import com.dsvag.androidacademyproject.models.movies.Movie
import com.dsvag.androidacademyproject.models.movies.Request
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiMovieService {
    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
    ): Response<Request>

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
    ): Response<Request>

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
    ): Response<Request>

    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Int,
        @Query("api_key") api_key: String,
    ): Response<Movie>

    @GET("movie/{id}/credits")
    suspend fun getCredits(
        @Path("id") id: Int,
        @Query("api_key") api_key: String,
    ): Response<Credits>

    @GET("search/movie")
    suspend fun search(
        @Query("api_key") api_key: String,
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Response<Request>
}