package com.dsvag.androidacademyproject.data.models

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Actor(
    val firstName: String,
    val lastName: String,
    val photo: Bitmap,
) : Parcelable