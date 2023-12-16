package com.example.people.helper

import java.io.Serializable

interface UnaryConsumer<T> : Serializable {
    operator fun invoke(response: T)
}