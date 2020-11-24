package com.dsvag.androidacademyproject.data.models

import android.graphics.drawable.Drawable
import java.io.Serializable

data class Movie(
    val name: String,
    val preview: Drawable,
    val preview2: Drawable,
    val tags: List<String>,
    val ageLimit: Int,
    val rating: Double,
    val reviews: Int,
    val storyLine: String,
    val cast: List<Actor>,
    val length: Int,
) : Serializable