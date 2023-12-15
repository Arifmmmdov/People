package com.example.people.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class PeopleResponse(
    @SerializedName("countryList")
    @Expose
    val countries:List<Country>
)

data class Country(
    @SerializedName("countryId")
    @Expose
    val id:Int,
    @SerializedName("name")
    @Expose
    val countryName:String,
    @SerializedName("cityList")
    @Expose
    val cities:List<City>
)

data class City(
    @SerializedName("cityId")
    @Expose
    val id:Int,
    @SerializedName("name")
    @Expose
    val cityName:String,
    @SerializedName("peopleList")
    @Expose
    val people:List<Person>
)

data class Person(
    @SerializedName("humanId")
    @Expose
    val id:Int,
    @SerializedName("name")
    @Expose
    val name:String,
    @SerializedName("surname")
    @Expose
    val surname:String
)