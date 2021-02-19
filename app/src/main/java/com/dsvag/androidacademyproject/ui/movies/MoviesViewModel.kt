package com.dsvag.androidacademyproject.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsvag.androidacademyproject.data.repositories.MovieRepository
import com.dsvag.androidacademyproject.models.movie.Movie
import com.dsvag.androidacademyproject.models.movie.MovieResponse
import com.dsvag.androidacademyproject.ui.movies.MoviesViewModel.QueryType.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _state = MutableLiveData<State>(State.Default)
    val state: LiveData<State> get() = _state

    private val movieList = mutableListOf<Movie>()

    private var currentQueryType = TopRated
    private var pageCounter = 1
    private var maxPageCounter = 1

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

    fun fetchNowPlaying() {
        _state.value = State.Loading

        viewModelScope.launch(exceptionHandler) {
            if (currentQueryType != Now) {
                pageCounter = 1
                currentQueryType = Now

                fetchFirstPage(movieRepository.getNowPlaying(pageCounter))
            }
        }
    }

    fun fetchPopular() {
        _state.value = State.Loading

        viewModelScope.launch(exceptionHandler) {
            if (currentQueryType != Popular) {
                pageCounter = 1
                currentQueryType = Popular

                fetchFirstPage(movieRepository.getPopular(pageCounter))
            }
        }
    }

    fun fetchTopRated() {
        _state.value = State.Loading

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
            _state.value = State.Loading
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
        movieList.apply {
            clear()
            addAll(movieResponse.movies)
        }

        _state.value = State.Success(movieList)
    }

    private fun fetchNextPage(movieResponse: MovieResponse) {
        maxPageCounter = movieResponse.totalResults
        movieList.addAll(movieResponse.movies)

        _state.value = State.Success(movieList)
    }

    sealed class State {
        object Default : State()
        object Loading : State()

        data class Success(val movies: List<Movie>) : State()
        data class Error(val msg: String) : State()
    }

    private enum class QueryType {
        Now, Popular, TopRated
    }
}