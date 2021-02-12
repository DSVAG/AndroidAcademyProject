package com.dsvag.androidacademyproject.ui.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsvag.androidacademyproject.data.repositories.MovieRepository
import com.dsvag.androidacademyproject.models.movie.Movie
import com.dsvag.androidacademyproject.models.movie.MovieResponse
import com.dsvag.androidacademyproject.ui.movies.MoviesViewModel.QueryType.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import kotlin.math.min

class MoviesViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    private val _mutableState = MutableLiveData<State>(State.Default)
    val state get() = _mutableState

    private val _mutableMovies = MutableLiveData<MutableList<Movie>>()
    val movies get() = _mutableMovies

    private var currentQueryType = TopRated
    private var pageCounter = 1
    private var maxPageCounter = 1

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is IOException -> {
                setState(State.Error("Something went wrong! Check network connection"))
            }
            is HttpException -> {
                setState(State.Error("Something went wrong! Try later"))
            }
        }
    }

    fun fetchNowPlaying() {
        setState(State.Loading)

        viewModelScope.launch(exceptionHandler) {
            if (currentQueryType != Now) {
                pageCounter = 1
                currentQueryType = Now

                fetchFirstPage(movieRepository.getNowPlaying(pageCounter))
            }
        }
    }

    fun fetchPopular() {
        setState(State.Loading)

        viewModelScope.launch(exceptionHandler) {
            if (currentQueryType != Popular) {
                pageCounter = 1
                currentQueryType = Popular

                fetchFirstPage(movieRepository.getPopular(pageCounter))
            }
        }
    }

    fun fetchTopRated() {
        setState(State.Loading)

        viewModelScope.launch(exceptionHandler) {
            if (currentQueryType != TopRated) {
                pageCounter = 1
                currentQueryType = TopRated

                fetchFirstPage(movieRepository.getTopRated(pageCounter))
            }
        }
    }

    fun nextPage() {
        if (pageCounter < maxPageCounter) {
            setState(State.Loading)
            pageCounter = min(pageCounter + 1, maxPageCounter)

            viewModelScope.launch(exceptionHandler) {
                when (currentQueryType) {
                    Now -> fetchNextPage(movieRepository.getNowPlaying(pageCounter))
                    Popular -> fetchNextPage(movieRepository.getPopular(pageCounter))
                    TopRated -> fetchNextPage(movieRepository.getTopRated(pageCounter))
                }
            }
        }
    }

    private fun fetchFirstPage(movieResponse: MovieResponse) {
        maxPageCounter = movieResponse.totalResults
        _mutableMovies.postValue(movieResponse.movies.toMutableList())

        setState(State.Success)
    }

    private fun fetchNextPage(movieResponse: MovieResponse) {
        val value = _mutableMovies.value?.plus(movieResponse.movies)

        maxPageCounter = movieResponse.totalResults
        _mutableMovies.value = value?.toMutableList()

        setState(State.Success)
    }

    private fun setState(state: State) {
        viewModelScope.launch {
            _mutableState.value = state
        }
    }

    sealed class State {
        object Default : State()
        object Loading : State()
        object Success : State()

        data class Error(val msg: String) : State()
    }

    private enum class QueryType {
        Now, Popular, TopRated
    }
}