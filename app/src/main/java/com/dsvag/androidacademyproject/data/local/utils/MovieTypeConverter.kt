package com.dsvag.androidacademyproject.data.local.utils

import androidx.room.TypeConverter
import com.dsvag.androidacademyproject.models.movies.Genre
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types


class MovieTypeConverter {
    @TypeConverter
    fun fromGenreIds(genreIds: List<Int>?): String = genreIds?.joinToString(", ") ?: ""

    @TypeConverter
    fun toGenreIds(genreIdsString: String) = genreIdsString.split(", ").map { it.toInt() }

    @TypeConverter
    fun fromGenres(genres: List<Genre>?): String {
        val moshi = Moshi.Builder().build()
        val genresType = Types.newParameterizedType(List::class.java, Genre::class.java)
        val jsonAdapter = moshi.adapter<List<Genre>>(genresType)

        return jsonAdapter.toJson(genres)
    }

    @TypeConverter
    fun toGenres(genresJson: String): List<Genre> {
        val moshi = Moshi.Builder().build()
        val genresType = Types.newParameterizedType(List::class.java, Genre::class.java)
        val jsonAdapter = moshi.adapter<List<Genre>>(genresType)

        return jsonAdapter.fromJson(genresJson).orEmpty()
    }
}