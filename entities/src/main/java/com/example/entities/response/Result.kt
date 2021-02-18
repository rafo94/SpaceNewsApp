package com.example.entities.response

sealed class Result<out S> {
    data class Success<S>(val data: S?) : Result<S>()
    data class Error<E>(val errors: NewsException) : Result<E>()
}