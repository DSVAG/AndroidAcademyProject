package com.dsvag.androidacademyproject.data.models

import android.graphics.drawable.Drawable
import java.io.Serializable

data class Actor(
    val firstName: String,
    val lastName: String,
    val photo: Drawable,
) : Serializable