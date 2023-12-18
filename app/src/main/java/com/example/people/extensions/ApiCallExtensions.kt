package com.example.people.extensions

import android.util.Log
import com.example.people.model.PeopleResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import kotlin.coroutines.resume


suspend fun Call<PeopleResponse>.awaitResult(): PeopleResponse {
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
                Log.d("MyTagHere", call.toString())
            }
        })
    }
}