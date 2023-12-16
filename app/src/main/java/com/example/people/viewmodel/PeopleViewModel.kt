package com.example.people.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.people.R
import com.example.people.db.DBCountry
import com.example.people.helper.FilterDialog
import com.example.people.helper.UnaryConsumer
import com.example.people.model.PeopleResponse
import com.example.people.repository.PeopleRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PeopleViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val repository: PeopleRepository,
    private val filterDialog: FilterDialog
) : ViewModel() {

    private val _data = MutableLiveData<List<DBCountry>>()
    val data: LiveData<List<DBCountry>?> get() = _data

    fun fetchData(isForced: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            if (isForced || repository.isEmpty())
                callBackgroundData()
            else
                _data.value = repository.getLocalCountries()
        }

    }

    private fun callBackgroundData() {
        repository.getCountries(object : UnaryConsumer<PeopleResponse> {
            override suspend fun invoke(response: PeopleResponse) {
                repository.insertAll(response.countries)
                _data.value = repository.getLocalCountries()
            }
        })
    }

    fun showFilterDialog(isCountry: Boolean, listener: FilterDialog.FilterDialogListener) {
        viewModelScope.launch {
            if (isCountry)
                filterDialog.showSelectListDialog(
                    context.getString(R.string.filter_country),
                    getCountriesName(), listener
                )
            else
                filterDialog.showSelectListDialog(
                    context.getString(R.string.filter_city),
                    getCitiesName(), listener
                )
        }
    }

    private suspend fun getCitiesName(): Array<String> {
        val citiesName = repository.getCityNames()
        return citiesName.toTypedArray()
    }

    private suspend fun getCountriesName(): Array<String> {
        val countriesList = repository.getCountriesName()
        return countriesList.toTypedArray()
    }
}