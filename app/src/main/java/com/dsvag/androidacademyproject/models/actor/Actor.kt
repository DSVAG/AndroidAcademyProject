package com.dsvag.androidacademyproject.models.actor

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Actor(
    @Json(name = "credit_type")
    val creditType: String,
    @Json(name = "department")
    val department: String,
    @Json(name = "job")
    val job: String,
    @Json(name = "media")
    val media: Media,
    @Json(name = "media_type")
    val mediaType: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "person")
    val person: Person
)