package com.example.people.di

import android.content.Context
import androidx.room.Room
import com.example.people.App
import com.example.people.db.PeopleDao
import com.example.people.db.RoomDB
import com.example.people.helper.FilterDialog
import com.example.people.network.PeopleAPIService
import com.example.people.repository.PeopleRepository
import com.example.people.viewmodel.PeopleViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object PeopleModule {

    @Provides
    fun getName(): String {
        return "Arif"
    }

    @Provides
    @ActivityContext
    fun provideAppContext(context: Context): Context = context

    @Provides
    fun getAPIService(): PeopleAPIService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://89.147.202.166:1153/")
            .build().create(PeopleAPIService::class.java)
    }

    @Provides
    fun getPeopleRepo(apiService: PeopleAPIService, userDao: PeopleDao): PeopleRepository {
        return PeopleRepository(apiService, userDao)
    }

    @Provides
    fun getViewModel(
        @ApplicationContext context: Context,
        repository: PeopleRepository,
        filterDialog: FilterDialog,
    ): PeopleViewModel {
        return PeopleViewModel(context, repository, filterDialog)
    }

    @Provides
    fun getRoomDB(@ApplicationContext appContext: Context): RoomDB {
        return Room.databaseBuilder(
            appContext,
            RoomDB::class.java, "database-name"
        ).build()
    }

    @Provides
    fun getDao(roomDB: RoomDB): PeopleDao {
        return roomDB.peopleDao()
    }

    @Provides
    fun getFilterDialog(): FilterDialog {
        return FilterDialog()
    }
}