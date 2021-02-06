package com.dsvag.androidacademyproject.models.actor

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KnownFor(
    @Json(name = "genre_ids")
    val genreIds: List<Double>,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "vote_count")
    val voteCount: Double,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "popularity")
    val popularity: Double,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "original_name")
    val originalName: String,
    @Json(name = "first_air_date")
    val firstAirDate: String,
    @Json(name = "origin_country")
    val originCountry: List<String>,
    @Json(name = "id")
    val id: Double,
    @Json(name = "media_type")
    val mediaType: String,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "video")
    val video: Boolean,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "adult")
    val adult: Boolean
)