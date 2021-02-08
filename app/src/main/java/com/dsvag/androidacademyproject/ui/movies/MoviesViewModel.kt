package com.dsvag.androidacademyproject.ui.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsvag.androidacademyproject.data.repositories.MovieRepository
import com.dsvag.androidacademyproject.models.movies.Movie
import com.dsvag.androidacademyproject.models.movies.Request
import com.dsvag.androidacademyproject.ui.movies.MoviesViewModel.QueryType.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    fun fetchNowPlaying() {
        setState(State.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            if (currentQueryType != Now) {
                pageCounter = 1
                currentQueryType = Now

                fetchFirstPage(runCatching { movieRepository.getNowPlaying(pageCounter) }.getOrNull())
            }
        }
    }

    fun fetchPopular() {
        setState(State.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            if (currentQueryType != Popular) {
                pageCounter = 1
                currentQueryType = Popular

                fetchFirstPage(runCatching { movieRepository.getPopular(pageCounter) }.getOrNull())
            }
        }
    }

    fun fetchTopRated() {
        setState(State.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            if (currentQueryType != TopRated) {
                pageCounter = 1
                currentQueryType = TopRated

                fetchFirstPage(runCatching { movieRepository.getTopRated(pageCounter) }.getOrNull())
            }
        }
    }

    fun nextPage() {
        if (pageCounter < maxPageCounter) {
            setState(State.Loading)
            pageCounter = min(pageCounter + 1, maxPageCounter)

            viewModelScope.launch(Dispatchers.IO) {

                when (currentQueryType) {
                    Now -> fetchNextPage(runCatching { movieRepository.getNowPlaying(pageCounter) }.getOrNull())
                    Popular -> fetchNextPage(runCatching { movieRepository.getPopular(pageCounter) }.getOrNull())
                    TopRated -> fetchNextPage(runCatching { movieRepository.getTopRated(pageCounter) }.getOrNull())
                }
            }
        }
    }

    private fun fetchFirstPage(request: Request?) {
        if (request != null) {
            maxPageCounter = request.totalResults
            _mutableMovies.postValue(request.movies.toMutableList())

            setState(State.Success)
        } else {
            setState(State.Error("Something went wrong! Check network connection"))
        }
    }

    private fun fetchNextPage(request: Request?) {
        if (request != null) {
            val value = _mutableMovies.value?.plus(request.movies)

            maxPageCounter = request.totalResults
            _mutableMovies.postValue(value?.toMutableList())

            setState(State.Success)
        } else {
            setState(State.Error("Something went wrong! Check network connection"))
        }
    }

    private fun setState(state: State) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
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