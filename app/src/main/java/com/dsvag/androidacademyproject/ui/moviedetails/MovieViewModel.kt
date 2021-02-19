package com.dsvag.androidacademyproject.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsvag.androidacademyproject.data.repositories.MovieRepository
import com.dsvag.androidacademyproject.models.movie.Movie
import com.dsvag.androidacademyproject.models.person.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> get() = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is IOException -> {
                _state.value = State.Error("Something went wrong! Check network connection")
            }
            is HttpException -> {
                _state.value = State.Error("Something went wrong! Server not responding, try later")
            }
        }
    }

    fun fetchMovie(movieId: Long) {
        _state.value = State.Loading

        viewModelScope.launch(exceptionHandler) {
            val movie = movieRepository.getMovie(movieId)
            val movieCredits = movieRepository.getMovieCredits(movie)

            _state.value = State.Success(movie, movieCredits)
        }
    }

    sealed class State {
        object Loading : State()

        data class Success(val movie: Movie, val movieCredits: List<Person>) : State()
        data class Error(val msg: String) : State()
    }
}