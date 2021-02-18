package com.dsvag.androidacademyproject.models.credits

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Cast(
    @Json(name = "id")
    val id: Long,

    @Json(name = "name")
    val name: String,

    @Json(name = "original_name")
    val originalName: String,

    @Json(name = "profile_path")
    val profilePath: String?,
)