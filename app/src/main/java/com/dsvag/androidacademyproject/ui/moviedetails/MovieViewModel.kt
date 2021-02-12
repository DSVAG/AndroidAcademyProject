package com.dsvag.androidacademyproject.ui.moviedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsvag.androidacademyproject.data.repositories.MovieRepository
import com.dsvag.androidacademyproject.models.credits.Cast
import com.dsvag.androidacademyproject.models.movie.Movie
import com.dsvag.androidacademyproject.models.person.Person
import com.dsvag.androidacademyproject.ui.movies.MoviesViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class MovieViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    private val _mutableMovie = MutableLiveData<Movie>()
    val movie get() = _mutableMovie

    private val _mutableCast = MutableLiveData<List<Cast>>()
    val cast get() = _mutableCast

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is IOException -> {
                // "Something went wrong! Check network connection"
            }
            is HttpException -> {
                //"Something went wrong! Try later"
            }
        }
    }

    fun fetchMovie(movieId: Long) {
        viewModelScope.launch(exceptionHandler) {
            val movie = movieRepository.getMovie(movieId)

            _mutableMovie.value = movie
            _mutableCast.value = movieRepository.getMovieCredits(movieId)
        }
    }
}