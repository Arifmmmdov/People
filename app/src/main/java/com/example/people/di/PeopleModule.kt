package com.example.people.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PeopleModule {

    @Provides
    fun name():String{
        return "Arif"
    }
}