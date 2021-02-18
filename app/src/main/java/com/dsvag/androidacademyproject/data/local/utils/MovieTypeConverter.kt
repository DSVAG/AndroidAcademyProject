package com.dsvag.androidacademyproject.data.local.utils

import androidx.room.TypeConverter
import com.dsvag.androidacademyproject.models.movie.Genre
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.internal.toLongOrDefault

class MovieTypeConverter {
    @TypeConverter
    fun fromLongList(ids: List<Long>) = ids.joinToString()

    @TypeConverter
    fun toLongList(genreIdsString: String): List<Long> {
        return genreIdsString.split(", ").map { it.toLongOrDefault(0) }
    }

    @TypeConverter
    fun fromGenres(genres: List<Genre>): String {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val type = Types.newParameterizedType(List::class.java, Genre::class.java)
        val jsonAdapter = moshi.adapter<List<Genre>>(type)

        return jsonAdapter.toJson(genres)
    }

    @TypeConverter
    fun toGenres(genresJson: String): List<Genre> {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val type = Types.newParameterizedType(List::class.java, Genre::class.java)
        val jsonAdapter = moshi.adapter<List<Genre>>(type)

        return jsonAdapter.fromJson(genresJson).orEmpty()
    }
}