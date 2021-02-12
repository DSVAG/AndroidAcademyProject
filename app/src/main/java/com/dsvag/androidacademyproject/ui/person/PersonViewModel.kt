package com.dsvag.androidacademyproject.ui.person

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsvag.androidacademyproject.data.repositories.PersonRepository
import com.dsvag.androidacademyproject.models.movie.Movie
import com.dsvag.androidacademyproject.models.person.Person
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class PersonViewModel @ViewModelInject constructor(
    private val personRepository: PersonRepository,
) : ViewModel() {
    private var _mutablePersonData: MutableLiveData<Person> = MutableLiveData()
    val personData get() = _mutablePersonData

    private var _mutablePersonMovieData: MutableLiveData<List<Movie>> = MutableLiveData()
    val personMovieData get() = _mutablePersonMovieData

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

    fun fetchPerson(personId: Long) {
        viewModelScope.launch(exceptionHandler) {
            val person = personRepository.getPersonWithMovies(personId)

            _mutablePersonData.value = person

            _mutablePersonMovieData.value = personRepository.getPersonMovies(person.moviesIds)
        }
    }
}