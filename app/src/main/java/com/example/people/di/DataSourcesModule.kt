package com.example.people.di

import android.content.Context
import androidx.room.Room
import com.example.people.db.PeopleDao
import com.example.people.db.RoomDB
import com.example.people.network.PeopleAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object DataSourcesModule {

    @Provides
    fun getDao(roomDB: RoomDB): PeopleDao {
        return roomDB.peopleDao()
    }

    @Provides
    fun getRoomDB(@ApplicationContext appContext: Context): RoomDB {
        return Room.databaseBuilder(
            appContext, RoomDB::class.java, "people_db"
        ).build()
    }


    @Provides
    fun getAPIService(): PeopleAPIService {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://89.147.202.166:1153/").build().create(PeopleAPIService::class.java)
    }

}