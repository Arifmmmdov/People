package com.example.people.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.people.R
import com.example.people.db.DBCity
import com.example.people.db.DBCountry
import com.example.people.extensions.cityPeople
import com.example.people.extensions.countryPeople
import com.example.people.extensions.filterCity
import com.example.people.extensions.filterCountry
import com.example.people.extensions.getCitiesName
import com.example.people.extensions.getCityNames
import com.example.people.extensions.getCountriesName
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
    private val filterDialog: FilterDialog,
) : ViewModel() {

    private val _data = MutableLiveData<List<DBCountry>>()
    val data: LiveData<List<DBCountry>?> get() = _data

    private val _filteredCountry = MutableLiveData<List<DBCountry>>()
    val filteredCountry: LiveData<List<DBCountry>?> get() = _filteredCountry

    private val _filteredCity = MutableLiveData<List<DBCity>>()
    val filteredCity: LiveData<List<DBCity>?> get() = _filteredCity


    fun fetchData(isForced: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            if (isForced || repository.isEmpty())
                callAPIData()
            else
                _data.value = repository.getLocalCountries()
        }

    }

    private fun callAPIData() {
        repository.getCountries(object : UnaryConsumer<PeopleResponse> {
            override suspend fun invoke(response: PeopleResponse) {
                repository.insertAll(response.countries)
                _data.value = repository.getLocalCountries()
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
            val filterData = if (isCountry) data.value?.getCountriesName() else getCitiesName()
            val filteredPeople =
                if (isCountry) filteredCountry.value?.getCountriesName() else filteredCity.value?.getCitiesName()

            val checkedItems: BooleanArray = filterData?.map {
                it in (filteredPeople ?: listOf())
            }!!.toBooleanArray()

            filterDialog.showSelectListDialog(
                context.getString(filterTitle),
                context,
                filterData,
                checkedItems,
                object : FilterDialog.FilterDialogListener {
                    override fun onItemsSelected(selectedItems: List<String>) {
                        filterItems(isCountry, selectedItems)
                    }
                }
            )
        }
    }

    private fun filterItems(isCountry: Boolean, selectedItems: List<String>) {
        if (isCountry)
            _filteredCountry.value = data.value!!.filterCountry(selectedItems)
        else
            _filteredCity.value = countries().filterCity(selectedItems)
    }

    private fun countries(): List<DBCountry> {
        return if (!filteredCountry.value.isNullOrEmpty())
            filteredCountry.value!!
        else
            data.value!!
    }


    private fun getCitiesName(): List<String> {
        var countries = filteredCountry.value
        if (countries.isNullOrEmpty())
            countries = data.value
        return countries!!.getCityNames()
    }

}