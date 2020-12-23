package com.dsvag.androidacademyproject.data.models

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Movie(
    val name: String,
    val preview: Bitmap,
    val tags: List<String>,
    val ageLimit: Int,
    val rating: Double,
    val reviews: Int,
    val storyLine: String,
    val cast: List<Actor>,
    val length: Int,
) : Parcelable