package com.dsvag.androidacademyproject.data.local.utils

import androidx.room.TypeConverter
import com.dsvag.androidacademyproject.models.movie.Genre
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MovieTypeConverter {
    @TypeConverter
    fun fromGenreIds(genreIds: List<Long>): String = genreIds.joinToString(", ")

    @TypeConverter
    fun toGenreIds(genreIdsString: String) = genreIdsString.split(", ").map { it.toLong() }

    @TypeConverter
    fun fromGenres(genres: List<Genre>): String {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val type = Types.newParameterizedType(List::class.java, Genre::class.java)
        val jsonAdapter = moshi.adapter<List<Genre>>(type)

        return jsonAdapter.toJson(genres)
    }

    @TypeConverter
    fun toGenres(genresJson: String): List<Genre> {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Genre::class.java)
        val jsonAdapter = moshi.adapter<List<Genre>>(type)

        return jsonAdapter.fromJson(genresJson).orEmpty()
    }
}