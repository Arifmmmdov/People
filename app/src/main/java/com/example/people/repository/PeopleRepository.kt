package com.example.people.repository


import com.example.people.helper.UnaryConsumer
import com.example.people.helper.awaitResult
import com.example.people.model.PeopleResponse
import com.example.people.network.PeopleAPIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.security.auth.callback.Callback

class PeopleRepository @Inject constructor(private val apiService: PeopleAPIService) {

    fun callCountriesAPI(): Call<PeopleResponse> {
        return apiService.getCountries()
    }

    suspend fun getCountriesAsync():PeopleResponse{
        return callCountriesAPI().awaitResult()
    }

    fun getCountries(
        onComplete: UnaryConsumer<PeopleResponse>
    ){
        CoroutineScope(Dispatchers.Main).launch {
            onComplete(getCountriesAsync())
        }
    }
}