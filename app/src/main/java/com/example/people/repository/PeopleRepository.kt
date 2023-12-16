package com.example.people.repository


import com.example.people.db.DBCountry
import com.example.people.db.PeopleDao
import com.example.people.extensions.toDBCountries
import com.example.people.helper.UnaryConsumer
import com.example.people.helper.awaitResult
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
    private val userDao: PeopleDao
) {

    private fun callCountriesAPI(): Call<PeopleResponse> {
        return apiService.getCountries()
    }

    private suspend fun getCountriesAsync(): PeopleResponse {
        return callCountriesAPI().awaitResult()
    }

    fun getCountries(
        onComplete: UnaryConsumer<PeopleResponse>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            onComplete(getCountriesAsync())
        }
    }

    suspend fun getLocalCountries(): List<DBCountry> {
        return withContext(Dispatchers.IO) {
            userDao.getAll()
        }
    }

    suspend fun isEmpty(): Boolean {
        return withContext(Dispatchers.IO){
            userDao.getCount() == 0
        }
    }

    suspend fun insertAll(countries: List<Country>) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.insertAll(countries.toDBCountries())
        }
    }
}