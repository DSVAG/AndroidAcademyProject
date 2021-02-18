package com.dsvag.androidacademyproject.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dsvag.androidacademyproject.models.movie.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieById(id: Long): Movie?

    @Query("SELECT * FROM movies WHERE id IN (:ids)")
    suspend fun getMoviesByIds(ids: List<Long>): List<Movie>

    @Query("SELECT id FROM movies")
    suspend fun getMoviesIds(): List<Long>
}