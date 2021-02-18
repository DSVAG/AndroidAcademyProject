package com.dsvag.androidacademyproject.models.person

import com.dsvag.androidacademyproject.models.movie.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonMovies(
    @Json(name = "id")
    val id: Long,

    @Json(name = "cast")
    val asCast: List<Movie>,
)