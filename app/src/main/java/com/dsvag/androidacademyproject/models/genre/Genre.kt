package com.dsvag.androidacademyproject.models.genre

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "genres")
@JsonClass(generateAdapter = true)
data class Genre(
    @PrimaryKey(autoGenerate = false)
    @Json(name = "id")
    @ColumnInfo(name = "id")
    val id: Long,

    @Json(name = "name")
    @ColumnInfo(name = "name")
    val name: String
)