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
    private val _mutableMovieData: MutableLiveData<Movie> = MutableLiveData()
    val movieData get() = _mutableMovieData

    private val _mutableCastData: MutableLiveData<List<Cast>> = MutableLiveData()
    val castData get() = _mutableCastData

    fun fetchMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = movieRepository.getMovie(movieId)

            if (response.isSuccessful && response.body() != null) {
                _mutableMovieData.postValue(response.body())
            }
        }
    }

    fun fetchCredits(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = movieRepository.getCredits(movieId)

            if (response.isSuccessful && response.body() != null) {
                _mutableCastData.postValue(response.body()!!.cast)
            }
        }
    }
}