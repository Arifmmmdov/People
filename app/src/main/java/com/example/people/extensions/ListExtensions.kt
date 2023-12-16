package com.example.people.extensions

import com.example.people.db.DBCity
import com.example.people.db.DBCountry
import com.example.people.db.DBPerson
import com.example.people.model.City
import com.example.people.model.Country
import com.example.people.model.Person

fun List<Country>.toDBCountries():  List<DBCountry> {
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


fun List<DBCountry>.getPeopleList(): List<DBPerson> {
    return this.flatMap {
        it.cities.flatMap { city ->
            city.people
        }
    }
}