package com.dsvag.androidacademyproject.models.person

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "persons")
@JsonClass(generateAdapter = true)
data class Person(
    @Json(name = "biography")
    @ColumnInfo(name = "biography")
    val biography: String,

    @Json(name = "birthday")
    @ColumnInfo(name = "birthday")
    val birthday: String?,

    @PrimaryKey(autoGenerate = false)
    @Json(name = "id")
    @ColumnInfo(name = "id")
    val id: Int,

    @Json(name = "known_for_department")
    @ColumnInfo(name = "known_for_department")
    val knownForDepartment: String,

    @Json(name = "name")
    @ColumnInfo(name = "name")
    val name: String,

    @Json(name = "place_of_birth")
    @ColumnInfo(name = "place_of_birth")
    val placeOfBirth: String?,

    @Json(name = "profile_path")
    @ColumnInfo(name = "profile_path")
    val profilePath: String?
)