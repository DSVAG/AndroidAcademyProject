package com.dsvag.androidacademyproject.ui.person

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsvag.androidacademyproject.data.repositories.PersonRepository
import com.dsvag.androidacademyproject.models.credits.Cast
import com.dsvag.androidacademyproject.models.person.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonViewModel @ViewModelInject constructor(
    private val personRepository: PersonRepository,
) : ViewModel() {
    private var _mutablePersonData: MutableLiveData<Person> = MutableLiveData()
    val personData get() = _mutablePersonData

    private val _mutablePersonMovieData: MutableLiveData<List<Cast>> = MutableLiveData()
    val personMovieData get() = _mutablePersonMovieData

    fun fetchPerson(personId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = runCatching { personRepository.getPerson(personId) }.getOrNull()

            if (response != null && response.isSuccessful && response.body() != null) {
                _mutablePersonData.postValue(response.body())
            }
        }
    }

    fun fetchPersonMovie(personId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = runCatching { personRepository.getPersonMovies(personId) }.getOrNull()

            if (response != null && response.isSuccessful && response.body() != null) {
                _mutablePersonMovieData.postValue(response.body()!!.cast)
            }
        }
    }
}