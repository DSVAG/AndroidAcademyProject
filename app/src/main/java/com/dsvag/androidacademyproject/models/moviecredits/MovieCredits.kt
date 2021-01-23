package com.dsvag.androidacademyproject.models.moviecredits

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieCredits(
    @Json(name = "cast")
    val cast: List<Cast>,
    @Json(name = "crew")
    val crew: List<Crew>,
    @Json(name = "id")
    val id: Int
)