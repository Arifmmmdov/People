package com.example.people.helper

import java.io.Serializable

public interface UnaryConsumer<T> : Serializable {
    operator fun invoke(response: T)
}