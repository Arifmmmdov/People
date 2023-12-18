package com.example.people.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface PeopleDao {
    @Query("SELECT * FROM people_table")
    fun getAll(): List<DBCountry>

    @Query("DELETE FROM people_table")
    fun deleteAll()

    @Query("Select country_name FROM people_table")
    fun getCountriesName(): List<String>

    @Query("SELECT COUNT(*) FROM people_table")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(people: List<DBCountry>)

}