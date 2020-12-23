package com.dsvag.androidacademyproject.data.models

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String,
    val backdrop: String,
    val ratings: Float,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val runtime: Int,
    val genres: List<Genre> = listOf(),
    val actors: List<Actor> = listOf(),
)