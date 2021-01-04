package com.dsvag.androidacademyproject.ui.fragments.movielist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsvag.androidacademyproject.data.repositories.MovieRepository
import com.dsvag.androidacademyproject.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _movieListData = MutableLiveData<List<Movie>>()
    val movieListData get() = _movieListData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _movieListData.postValue(movieRepository.fetchMovies())
        }
    }
}