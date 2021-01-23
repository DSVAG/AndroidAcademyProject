package com.dsvag.androidacademyproject.models.moviecredits

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Crew(
    @Json(name = "id")
    val id: Int,
    @Json(name = "department")
    val department: String,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "job")
    val job: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "vote_count")
    val voteCount: Int,
    @Json(name = "video")
    val video: Boolean,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "title")
    val title: String,
    @Json(name = "popularity")
    val popularity: Double,
    @Json(name = "genre_ids")
    val genreIds: List<Int>,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "adult")
    val adult: Boolean,
    @Json(name = "release_date")
    val releaseDate: String?,
    @Json(name = "credit_id")
    val creditId: String
)