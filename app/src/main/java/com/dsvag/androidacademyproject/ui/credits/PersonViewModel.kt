package com.dsvag.androidacademyproject.ui.credits

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsvag.androidacademyproject.data.repositories.PersonRepository
import com.dsvag.androidacademyproject.models.moviecredits.Cast
import com.dsvag.androidacademyproject.models.person.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonViewModel @ViewModelInject constructor(
    private val personRepository: PersonRepository,
) : ViewModel() {
    private val _mutablePersonData: MutableLiveData<Person> = MutableLiveData()
    val personData get() = _mutablePersonData

    private val _mutablePersonMovieData: MutableLiveData<List<Cast>> = MutableLiveData()
    val personMovieData get() = _mutablePersonMovieData

    fun fetchPerson(personId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _mutablePersonData.postValue(personRepository.getPerson(personId))
        }
    }

    fun fetchPersonMovie(personId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _mutablePersonMovieData.postValue(personRepository.getPersonMovies(personId)?.cast)
        }
    }
}