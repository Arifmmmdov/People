package com.example.people.di

import android.provider.Contacts.People
import com.example.people.network.PeopleAPIService
import com.example.people.repository.PeopleRepository
import com.example.people.viewmodel.PeopleViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class PeopleModule {

    @Provides
    fun getName():String{
        return "Arif"
    }

    @Provides
    fun getAPIService():PeopleAPIService{
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://89.147.202.166:1153/")
            .build().create(PeopleAPIService::class.java)
    }

    @Provides
    fun getPeopleRepo(apiService: PeopleAPIService) :PeopleRepository{
        return PeopleRepository(apiService)
    }
    @Provides
    fun getViewModel(repo:PeopleRepository) : PeopleViewModel {
        return PeopleViewModel(repo)
    }
}