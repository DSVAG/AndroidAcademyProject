package com.dsvag.androidacademyproject.ui.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsvag.androidacademyproject.data.repositories.MovieRepository
import com.dsvag.androidacademyproject.models.movies.Result
import com.dsvag.androidacademyproject.ui.movies.MoviesViewModel.QueryType.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.min

class MoviesViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    private val _mutableState = MutableLiveData<State>(State.Default)
    val state get() = _mutableState

    private val _mutableResult = MutableLiveData<List<Result>>()
    val result get() = _mutableResult

    private var currentQueryType = TopRated
    private var pageCounter = 1
    private var maxPageCounter = 1
    private var searchQuery: String? = null

    fun fetchNowPlaying() {
        setState(State.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            if (currentQueryType != NowPlaying) {
                pageCounter = 1
                currentQueryType = NowPlaying

                val response = movieRepository.getNowPlaying(pageCounter)

                if (response.isSuccessful && response.body() != null) {
                    maxPageCounter = response.body()!!.totalResults
                    _mutableResult.postValue(response.body()!!.results)

                    setState(State.Success)
                } else {
                    setState(State.Error(response.errorBody().toString()))
                }
            } else {
                val response = movieRepository.getNowPlaying(pageCounter)

                if (response.isSuccessful && response.body() != null) {
                    val value = _mutableResult.value?.plus(response.body()!!.results)

                    maxPageCounter = response.body()!!.totalResults
                    _mutableResult.postValue(value)
                    setState(State.Success)
                } else {
                    setState(State.Error(response.errorBody().toString()))
                }
            }
        }
    }

    fun fetchPopular() {
        setState(State.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            if (currentQueryType != Popular) {
                pageCounter = 1
                currentQueryType = Popular

                val response = movieRepository.getPopular(pageCounter)

                if (response.isSuccessful && response.body() != null) {
                    maxPageCounter = response.body()!!.totalResults
                    _mutableResult.postValue(response.body()!!.results)
                    setState(State.Success)
                } else {
                    setState(State.Error(response.errorBody().toString()))
                }

            } else {
                val response = movieRepository.getPopular(pageCounter)

                if (response.isSuccessful && response.body() != null) {
                    val value = _mutableResult.value?.plus(response.body()!!.results)

                    maxPageCounter = response.body()!!.totalResults
                    _mutableResult.postValue(value)
                    setState(State.Success)
                } else {
                    setState(State.Error(response.errorBody().toString()))
                }
            }
        }
    }

    fun fetchTopRated() {
        setState(State.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            if (currentQueryType != TopRated) {
                pageCounter = 1
                currentQueryType = TopRated

                val response = movieRepository.getTopRated(pageCounter)

                if (response.isSuccessful && response.body() != null) {
                    maxPageCounter = response.body()!!.totalResults
                    _mutableResult.postValue(response.body()!!.results)
                    setState(State.Success)
                } else {
                    setState(State.Error(response.errorBody().toString()))
                }

            } else {
                val response = movieRepository.getTopRated(pageCounter)

                if (response.isSuccessful && response.body() != null) {
                    val value = _mutableResult.value?.plus(response.body()!!.results)

                    maxPageCounter = response.body()!!.totalResults
                    _mutableResult.postValue(value)
                    setState(State.Success)
                } else {
                    setState(State.Error(response.errorBody().toString()))
                }
            }
        }
    }

    fun search(query: String?) {
        searchQuery = query
        setState(State.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            if (searchQuery != null) {
                if (currentQueryType != Search) {
                    pageCounter = 1
                    currentQueryType = Search

                    val response = movieRepository.search(query!!, pageCounter)

                    if (response.isSuccessful && response.body() != null) {

                        maxPageCounter = response.body()!!.totalResults
                        _mutableResult.postValue(response.body()!!.results)
                        setState(State.Success)
                    } else {
                        setState(State.Error(response.errorBody().toString()))
                    }
                } else {
                    val response = movieRepository.search(query!!, pageCounter)

                    if (response.isSuccessful && response.body() != null) {
                        val value = _mutableResult.value?.plus(response.body()!!.results)

                        maxPageCounter = response.body()!!.totalResults
                        _mutableResult.postValue(value)
                        setState(State.Success)
                    } else {
                        setState(State.Error(response.errorBody().toString()))
                    }
                }
            }
        }
    }

    fun nextPage() {
        pageCounter = min(pageCounter + 1, maxPageCounter)

        when (currentQueryType) {
            NowPlaying -> fetchNowPlaying()
            Popular -> fetchPopular()
            TopRated -> fetchTopRated()
            Search -> search(searchQuery)
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
        NowPlaying, Popular, TopRated, Search
    }
}