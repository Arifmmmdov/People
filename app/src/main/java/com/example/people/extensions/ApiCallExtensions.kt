package com.example.people.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import com.example.people.App
import com.example.people.helper.NetworkHelper
import com.example.people.model.PeopleResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import kotlin.coroutines.resume


suspend fun Call<PeopleResponse>.awaitResult(
    context: Context,
    networkHelper: NetworkHelper,
): PeopleResponse {
    return suspendCancellableCoroutine {
        this.enqueue(object : Callback<PeopleResponse> {

            override fun onResponse(
                call: Call<PeopleResponse>,
                response: retrofit2.Response<PeopleResponse>,
            ) {
                val body = response.body()
                it.resume(body!!)
            }

            override fun onFailure(call: Call<PeopleResponse>, t: Throwable) {
                it.resume(PeopleResponse(listOf()))
                networkHelper.checkInternetConnection()
            }
        })
    }
}