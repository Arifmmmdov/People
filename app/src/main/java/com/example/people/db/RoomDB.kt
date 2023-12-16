package com.example.people.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.people.db.type_converter.CityConverter

@Database(entities = [DBCountry::class], version = 1)
@TypeConverters(CityConverter::class)
abstract class RoomDB : RoomDatabase() {
    abstract fun peopleDao(): PeopleDao
}