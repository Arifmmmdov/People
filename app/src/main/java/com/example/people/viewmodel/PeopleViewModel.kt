package com.example.people.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.people.helper.UnaryConsumer
import com.example.people.model.Country
import com.example.people.model.PeopleResponse
import com.example.people.repository.PeopleRepository
import javax.inject.Inject

class PeopleViewModel @Inject constructor(private val repository: PeopleRepository) : ViewModel() {

    private val _data = MutableLiveData<List<Country>>()
    val data: LiveData<List<Country>?> get() = _data

    fun fetchData() {
        repository.getCountries(object : UnaryConsumer<PeopleResponse> {
            override fun invoke(response: PeopleResponse) {
                _data.value = response.countries
            }
        })
    }
}