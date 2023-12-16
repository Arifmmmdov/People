package com.example.people.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.people.db.DBCountry
import com.example.people.helper.UnaryConsumer
import com.example.people.model.PeopleResponse
import com.example.people.repository.PeopleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PeopleViewModel @Inject constructor(private val repository: PeopleRepository) : ViewModel() {

    private val _data = MutableLiveData<List<DBCountry>>()
    val data: LiveData<List<DBCountry>?> get() = _data

    fun fetchData(isForced: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            if (isForced || repository.isEmpty())
                repository.getCountries(object : UnaryConsumer<PeopleResponse> {
                    override suspend fun invoke(response: PeopleResponse) {
                        repository.insertAll(response.countries)
                        _data.value = repository.getLocalCountries()
                    }
                })
            else
                _data.value = repository.getLocalCountries()
        }

    }
}