package com.dsvag.androidacademyproject.models.person

import com.dsvag.androidacademyproject.models.movies.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonMovies(
    @Json(name = "id")
    val id: Int,

    @Json(name = "cast")
    val asCast: List<Movie>,
)