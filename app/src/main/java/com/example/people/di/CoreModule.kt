package com.example.people.di

import android.content.Context
import com.example.people.App
import com.example.people.db.PeopleDao
import com.example.people.helper.FilterDialog
import com.example.people.helper.NetworkHelper
import com.example.people.network.PeopleAPIService
import com.example.people.repository.PeopleRepository
import com.example.people.viewmodel.PeopleViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    fun getName(): String {
        return "Arif"
    }

//    @Provides
//    fun provideContext(@ActivityContext appContext: Context): Context {
//        return appContext
//    }

    @Provides
    fun getPeopleRepo(
        apiService: PeopleAPIService,
        userDao: PeopleDao,
        networkHelper: NetworkHelper,
        @ApplicationContext context: Context,
    ): PeopleRepository {
        return PeopleRepository(apiService, userDao, networkHelper, context)
    }

    @Provides
    fun getViewModel(
        repository: PeopleRepository,
        filterDialog: FilterDialog,
    ): PeopleViewModel {
        return PeopleViewModel(repository, filterDialog)
    }

    @Provides
    fun getFilterDialog(): FilterDialog {
        return FilterDialog()
    }
}