package com.dsvag.androidacademyproject.models.movies

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Request(
    @Json(name = "dates")
    val dates: Dates?,

    @Json(name = "page")
    val page: Int,

    @Json(name = "results")
    val results: List<Result>,

    @Json(name = "total_pages")
    val totalPages: Int,

    @Json(name = "total_results")
    val totalResults: Int
)