package com.example.people.db.type_converter

import androidx.room.TypeConverter
import com.example.people.db.DBPerson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PersonListConverter {

    @TypeConverter
    fun fromString(value: String): List<DBPerson> {
        val listType = object : TypeToken<List<DBPerson>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<DBPerson>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}