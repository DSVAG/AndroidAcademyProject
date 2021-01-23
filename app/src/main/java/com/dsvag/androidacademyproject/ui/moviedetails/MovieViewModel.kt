package com.dsvag.androidacademyproject.ui.moviedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsvag.androidacademyproject.data.repositories.MovieRepository
import com.dsvag.androidacademyproject.models.credits.Cast
import com.dsvag.androidacademyproject.models.movie.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    private val _mutableMovie = MutableLiveData<Movie>()
    val movie get() = _mutableMovie

    private val _mutableCast = MutableLiveData<List<Cast>>()
    val cast get() = _mutableCast

    fun fetchMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = movieRepository.getMovie(movieId)

            if (response.isSuccessful && response.body() != null) {
                _mutableMovie.postValue(response.body())
            }
        }
    }

    fun fetchCredits(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = movieRepository.getCredits(movieId)

            if (response.isSuccessful && response.body() != null) {
                _mutableCast.postValue(response.body()!!.cast)
            }
        }
    }
}