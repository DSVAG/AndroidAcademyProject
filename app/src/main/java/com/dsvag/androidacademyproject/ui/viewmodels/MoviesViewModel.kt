package com.dsvag.androidacademyproject.ui.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.dsvag.androidacademyproject.data.di.getAppComponent
import com.dsvag.androidacademyproject.data.repositories.MovieRepository
import com.dsvag.androidacademyproject.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun fetchMovies(): LiveData<List<Movie>> {
        val result = MutableLiveData<List<Movie>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(movieRepository.fetchMovies())
        }

        return result
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MoviesViewModel(app.getAppComponent().movieRepository) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }
}