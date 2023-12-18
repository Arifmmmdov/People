package com.example.people.repository


import com.example.people.db.DBCountry
import com.example.people.db.PeopleDao
import com.example.people.extensions.toDBCountries
import com.example.people.helper.UnaryConsumer
import com.example.people.extensions.awaitResult
import com.example.people.model.Country
import com.example.people.model.PeopleResponse
import com.example.people.network.PeopleAPIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import javax.inject.Inject

class PeopleRepository @Inject constructor(
    private val apiService: PeopleAPIService,
    private val userDao: PeopleDao,
) {

    private fun callCountriesApi(): Call<PeopleResponse> {
        return apiService.getCountries()
    }

    private suspend fun getCountriesAsync(): PeopleResponse {
        return callCountriesApi().awaitResult()
    }

    fun getCountriesFromApi(
        onComplete: UnaryConsumer<PeopleResponse>,
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            onComplete(getCountriesAsync())
        }
    }

    suspend fun getAll(): List<DBCountry> {
        return withContext(Dispatchers.IO) {
            userDao.getAll()
        }
    }

    suspend fun isDBEmpty(): Boolean {
        return withContext(Dispatchers.IO) {
            userDao.getCount() == 0
        }
    }

    suspend fun insertAll(countries: List<Country>) {
        withContext(Dispatchers.IO) {
            userDao.insertAll(countries.toDBCountries())
        }
    }
}