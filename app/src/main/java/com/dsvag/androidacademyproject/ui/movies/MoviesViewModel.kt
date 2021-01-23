package com.dsvag.androidacademyproject.ui.movies

import android.util.Log
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
    private val _mutableResultData: MutableLiveData<List<Result>> = MutableLiveData()
    val resultData get() = _mutableResultData

    init {
        fetchNowPlaying()
    }

    private var currentQueryType = TopRated
    private var pageCounter = 1
    private var maxPageCounter = 1

    private var searchQuery: String? = null

    fun fetchNowPlaying() {
        viewModelScope.launch(Dispatchers.IO) {
            if (currentQueryType != NowPlaying) {
                pageCounter = 1
                currentQueryType = NowPlaying

                val value = movieRepository.getNowPlaying(1)
                maxPageCounter = value?.totalPages ?: 1

                _mutableResultData.postValue(value?.results)
            } else {
                val value =
                    _mutableResultData.value?.plus(movieRepository.getNowPlaying(pageCounter)!!.results)

                _mutableResultData.postValue(value)
            }
        }
    }

    fun fetchPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            if (currentQueryType != Popular) {
                pageCounter = 1
                currentQueryType = Popular

                val value = movieRepository.getPopular(1)
                maxPageCounter = value?.totalPages ?: 1

                _mutableResultData.postValue(value?.results)
            } else {
                val value =
                    _mutableResultData.value?.plus(movieRepository.getPopular(pageCounter)!!.results)

                _mutableResultData.postValue(value)
            }
        }
    }

    fun fetchTopRated() {
        viewModelScope.launch(Dispatchers.IO) {
            if (currentQueryType != TopRated) {
                pageCounter = 1
                currentQueryType = TopRated

                val value = movieRepository.getTopRated(1)
                maxPageCounter = value?.totalPages ?: 1

                _mutableResultData.postValue(value?.results)
            } else {
                val value =
                    _mutableResultData.value?.plus(movieRepository.getTopRated(pageCounter)!!.results)

                _mutableResultData.postValue(value)
            }
        }
    }

    fun search(query: String?) {
        searchQuery = query
        Log.d("kek", "search")

        viewModelScope.launch(Dispatchers.IO) {
            if (searchQuery != null) {
                if (currentQueryType != Search) {
                    pageCounter = 1
                    currentQueryType = Search

                    val value = movieRepository.search(query!!, 1)
                    maxPageCounter = value?.totalPages ?: 1

                    _mutableResultData.postValue(value?.results)
                } else {
                    val value =
                        _mutableResultData.value?.plus(
                            movieRepository.search(query!!, pageCounter)!!.results
                        )

                    _mutableResultData.postValue(value)
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

    private enum class QueryType {
        NowPlaying, Popular, TopRated, Search
    }
}