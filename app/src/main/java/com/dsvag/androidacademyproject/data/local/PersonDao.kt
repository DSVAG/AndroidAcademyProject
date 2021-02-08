package com.dsvag.androidacademyproject.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dsvag.androidacademyproject.models.person.Person

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(person: Person)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(persons: List<Person>)

    @Query("SELECT * FROM persons WHERE id = :id")
    fun getPersonById(id: Long): Person
}