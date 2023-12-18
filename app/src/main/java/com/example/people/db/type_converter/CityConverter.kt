package com.example.people.db.type_converter

import androidx.room.TypeConverter
import com.example.people.db.DBCity
import com.example.people.db.DBCountry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CityConverter {

    @TypeConverter
    fun fromCitiesList(cities: List<DBCity>?): String? {
        val gson = Gson()
        return gson.toJson(cities)
    }

    @TypeConverter
    fun toCitiesList(citiesString: String?): List<DBCity>? {
        val gson = Gson()
        val type = object : TypeToken<List<DBCity>>() {}.type
        return gson.fromJson(citiesString, type)
    }
}