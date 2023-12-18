package com.example.people.extensions

import com.example.people.db.DBCity
import com.example.people.db.DBCountry
import com.example.people.db.DBPerson
import com.example.people.model.City
import com.example.people.model.Country
import com.example.people.model.Person

fun List<Country>.toDBCountries(): List<DBCountry> {
    return this.map { it.toDBCountry() }
}

fun Country.toDBCountry(): DBCountry {
    return DBCountry(
        id = id,
        countryName = countryName,
        cities = cities.map { it.toDBCity() }
    )
}

fun City.toDBCity(): DBCity {
    return DBCity(
        id = id,
        cityName = cityName,
        people = people.map { it.toDBPerson() }
    )
}

fun Person.toDBPerson(): DBPerson {
    return DBPerson(
        id = id,
        name = name,
        surname = surname
    )
}


fun List<DBCountry>.countryPeople(): List<DBPerson> {
    return this.flatMap {
        it.cities.flatMap { city ->
            city.people
        }
    }
}

fun List<DBCity>.cityPeople(): List<DBPerson> {
    return this.flatMap { city ->
        city.people
    }
}

fun List<DBCountry>.filterCity(selectedItems: List<String>): List<DBCity> =
    if (selectedItems.isEmpty()) flatMap { it.cities }
    else flatMap { country -> country.cities.filter { it.cityName in selectedItems } }


fun List<DBCountry>.filterCountry(selectedItems: List<String>): List<DBCountry> =
    if (selectedItems.isEmpty()) this else filter { it.countryName in selectedItems }

fun List<DBCountry>.getCityNames(): List<String> {
    return this
        .flatMap {
            it.cities
        }
        .map { it.cityName }
}


fun List<DBCountry>.getCountriesName(): List<String> {
    return this.map {
        it.countryName
    }
}


fun List<DBCity>.getCitiesName(): List<String> {
    return this.map {
        it.cityName
    }
}

