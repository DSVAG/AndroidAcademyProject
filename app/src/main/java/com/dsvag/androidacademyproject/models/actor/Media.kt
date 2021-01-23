package com.dsvag.androidacademyproject.models.actor

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Media(
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "video")
    val video: Boolean,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "release_date")
    val releaseDate: String?,
    @Json(name = "vote_count")
    val voteCount: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "adult")
    val adult: Boolean,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "id")
    val id: Int,
    @Json(name = "genre_ids")
    val genreIds: List<Int>,
    @Json(name = "popularity")
    val popularity: Double,
    @Json(name = "character")
    val character: String
)