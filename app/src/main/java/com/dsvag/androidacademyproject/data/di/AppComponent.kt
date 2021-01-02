package com.dsvag.androidacademyproject.data.di

import android.app.Application
import com.dsvag.androidacademyproject.data.repositories.MovieRepository

class AppComponent(application: Application) {

    val movieRepository by lazy { MovieRepository(application.applicationContext) }

}