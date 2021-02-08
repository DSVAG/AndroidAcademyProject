package com.dsvag.androidacademyproject.models.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "movies")
@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "adult")
    @ColumnInfo(name = "adult")
    val adult: Boolean,

    @Json(name = "backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,

    @Json(name = "genre_ids")
    @ColumnInfo(name = "genre_ids")
    val genreIds: List<Long> = emptyList(),

    @Json(name = "genres")
    @ColumnInfo(name = "genres")
    val genres: List<Genre> = emptyList(),

    @PrimaryKey(autoGenerate = false)
    @Json(name = "id")
    @ColumnInfo(name = "id")
    val id: Long,

    @Json(name = "overview")
    @ColumnInfo(name = "overview")
    val overview: String,

    @Json(name = "poster_path")
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,

    @Json(name = "title")
    @ColumnInfo(name = "title")
    val title: String,

    @Json(name = "vote_average")
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,

    @Json(name = "vote_count")
    @ColumnInfo(name = "vote_count")
    val voteCount: Int,

    @ColumnInfo(name = "cast_ids")
    val castIds: List<Long> = emptyList()
)