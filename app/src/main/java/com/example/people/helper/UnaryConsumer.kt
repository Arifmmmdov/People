package com.example.people.helper

import java.io.Serializable

interface UnaryConsumer<T> : Serializable {
    suspend operator fun invoke(response: T)
}