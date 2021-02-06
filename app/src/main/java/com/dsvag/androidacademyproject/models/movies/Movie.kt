package com.dsvag.androidacademyproject.models.movies

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "adult")
    val adult: Boolean,

    @Json(name = "backdrop_path")
    val backdropPath: String?,

    @Json(name = "genre_ids")
    val genreIds: List<Int>?,

    @Json(name = "genres")
    val genre: List<Genre>?,

    @Json(name = "id")
    val id: Int,

    @Json(name = "overview")
    val overview: String,

    @Json(name = "poster_path")
    val posterPath: String?,

    @Json(name = "title")
    val title: String,

    @Json(name = "vote_average")
    val voteAverage: Double,

    @Json(name = "vote_count")
    val voteCount: Int
)