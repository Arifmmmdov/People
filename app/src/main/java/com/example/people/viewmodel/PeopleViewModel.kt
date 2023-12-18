package com.example.people.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.people.R
import com.example.people.db.DBCity
import com.example.people.db.DBCountry
import com.example.people.extensions.filterCity
import com.example.people.extensions.filterCountry
import com.example.people.extensions.toCitiesName
import com.example.people.extensions.toCityNames
import com.example.people.extensions.toCountryNames
import com.example.people.helper.FilterDialog
import com.example.people.helper.UnaryConsumer
import com.example.people.model.PeopleResponse
import com.example.people.repository.PeopleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val repository: PeopleRepository,
    private val filterDialog: FilterDialog,
) : ViewModel() {

    private val _countryList = MutableLiveData<List<DBCountry>>()
    val countryList: LiveData<List<DBCountry>?> get() = _countryList

    private val _filteredCountry = MutableLiveData<List<DBCountry>>()
    val filteredCountry: LiveData<List<DBCountry>?> get() = _filteredCountry

    private val _filteredCity = MutableLiveData<List<DBCity>>()
    val filteredCity: LiveData<List<DBCity>?> get() = _filteredCity


    fun fetchData(isForced: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            if (isForced || repository.isDBEmpty())
                callAPIData()
            else
                _countryList.value = repository.getAll()
        }

    }

    private fun callAPIData() {
        repository.getCountriesFromApi(object : UnaryConsumer<PeopleResponse> {
            override suspend fun invoke(response: PeopleResponse) {
                repository.insertAll(response.countries)
                _countryList.value = repository.getAll()
                resetFilter()
            }
        })
    }

    private fun resetFilter() {
        _filteredCity.value = listOf()
        _filteredCountry.value = listOf()
    }

    fun showFilterDialog(isCountry: Boolean, context: Context) {
        viewModelScope.launch {
            val filterTitle = if (isCountry) R.string.filter_country else R.string.filter_city
            val filterData = if (isCountry) countryList.value?.toCountryNames() else getCitiesName()
            val filteredNames =
                if (isCountry) filteredCountry.value?.toCountryNames() else filteredCity.value?.toCitiesName()



            filterDialog.showSelectListDialog(
                context.getString(filterTitle),
                context,
                filterData!!,
                filteredNames,
                object : FilterDialog.FilterDialogListener {
                    override fun onItemsSelected(selectedItems: List<String>) {
                        filterItems(isCountry, selectedItems)
                    }
                }
            )
        }
    }

    private fun filterItems(isCountry: Boolean, selectedItems: List<String>) {
        if (isCountry){
            _filteredCountry.value = countryList.value!!.filterCountry(selectedItems)
            _filteredCity.value = listOf()
        }
        else
            _filteredCity.value = filteredCountries().filterCity(selectedItems)
    }

    private fun filteredCountries(): List<DBCountry> {
        return if (!filteredCountry.value.isNullOrEmpty())
            filteredCountry.value!!
        else
            countryList.value!!
    }


    private fun getCitiesName(): List<String> {
        var countries = filteredCountry.value
        if (countries.isNullOrEmpty())
            countries = countryList.value
        return countries!!.toCityNames()
    }

}