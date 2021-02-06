package com.dsvag.androidacademyproject.models.credits

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Credits(
    @Json(name = "id")
    val id: Int,

    @Json(name = "cast")
    val cast: List<Cast>,

    @Json(name = "crew")
    val crew: List<Crew>
)