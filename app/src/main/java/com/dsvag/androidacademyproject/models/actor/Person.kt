package com.dsvag.androidacademyproject.models.actor


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Person(
    @Json(name = "known_for_department")
    val knownForDepartment: String,
    @Json(name = "profile_path")
    val profilePath: String?,
    @Json(name = "known_for")
    val knownFor: List<KnownFor>,
    @Json(name = "name")
    val name: String,
    @Json(name = "adult")
    val adult: Boolean,
    @Json(name = "id")
    val id: Int,
    @Json(name = "gender")
    val gender: Int,
    @Json(name = "popularity")
    val popularity: Double
)