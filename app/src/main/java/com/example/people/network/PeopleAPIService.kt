package com.example.people.network

import com.example.people.model.PeopleResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface PeopleAPIService {

    @GET("/tayqa/tiger/api/development/test/TayqaTech/getdata")
    fun getCountries(): Call<PeopleResponse>
}