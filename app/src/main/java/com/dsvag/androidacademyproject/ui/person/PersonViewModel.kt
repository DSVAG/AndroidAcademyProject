package com.dsvag.androidacademyproject.ui.person

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsvag.androidacademyproject.data.repositories.PersonRepository
import com.dsvag.androidacademyproject.models.movie.Movie
import com.dsvag.androidacademyproject.models.person.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonViewModel @ViewModelInject constructor(
    private val personRepository: PersonRepository,
) : ViewModel() {
    private var _mutablePersonData: MutableLiveData<Person> = MutableLiveData()
    val personData get() = _mutablePersonData

    private val _mutablePersonMovieData: MutableLiveData<List<Movie>> = MutableLiveData()
    val personMovieData get() = _mutablePersonMovieData

    fun fetchPerson(personId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val person = runCatching { personRepository.getPerson(personId) }.getOrNull()

            if (person != null) {
                _mutablePersonData.postValue(person)
            }
        }
    }

    fun fetchPersonMovie(personId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val personMovies =
                runCatching { personRepository.getPersonMovies(personId) }.getOrNull()

            if (personMovies != null) {
                _mutablePersonMovieData.postValue(personMovies.asCast)
            }
        }
    }
}