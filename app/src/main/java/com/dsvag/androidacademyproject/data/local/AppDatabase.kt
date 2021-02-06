package com.dsvag.androidacademyproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dsvag.androidacademyproject.data.local.utils.MovieTypeConverter
import com.dsvag.androidacademyproject.models.movies.Movie
import com.dsvag.androidacademyproject.models.person.Person

@Database(entities = [Movie::class, Person::class], version = 1)
@TypeConverters(MovieTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun personDao(): PersonDao
}