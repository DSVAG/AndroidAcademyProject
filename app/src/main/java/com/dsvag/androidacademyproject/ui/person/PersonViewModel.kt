package com.dsvag.androidacademyproject.ui.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsvag.androidacademyproject.data.repositories.PersonRepository
import com.dsvag.androidacademyproject.models.movie.Movie
import com.dsvag.androidacademyproject.models.person.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val personRepository: PersonRepository,
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

    fun fetchPerson(personId: Long) {
        _state.value = State.Loading

        viewModelScope.launch(exceptionHandler) {
            val person = personRepository.getPersonWithMovies(personId)
            val personMovie = personRepository.getPersonMovies(person.moviesIds)

            _state.value = State.Success(person, personMovie)
        }
    }

    sealed class State {
        object Loading : State()

        data class Success(val person: Person, val personMovie: List<Movie>) : State()
        data class Error(val msg: String) : State()
    }
}