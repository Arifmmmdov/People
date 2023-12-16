package com.example.people.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.people.db.type_converter.CityConverter

//data class DBCountries(
//    @PrimaryKey(autoGenerate = true) val id: Int,
//    @ColumnInfo(name = "countries")
//    val countries: List<DBCountry>
//)

@Entity(tableName = "people_table")
data class DBCountry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "country_name")
    val countryName: String,
    @TypeConverters(CityConverter::class)
    @ColumnInfo(name = "cities")
    val cities: List<DBCity>
)

data class DBCity(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "city_name")
    val cityName: String,
    @ColumnInfo(name = "people")
    val people: List<DBPerson>
)

data class DBPerson(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "surname")
    val surname: String
)