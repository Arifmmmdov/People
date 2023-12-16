package com.example.people.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Dao
interface PeopleDao {
    @Query("SELECT * FROM people_table")
    fun getAll(): List<DBCountry>

//    @Transaction
//    fun updateAll(countries: DBCountries) {
//        deleteAll()
//        insertAll(countries)
//    }

    @Insert
    fun insert(countries: DBCountry)

    @Query("DELETE FROM people_table")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM people_table")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(people: List<DBCountry>)


}