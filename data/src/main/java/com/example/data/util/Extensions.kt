package com.example.data.util

import com.example.entities.response.NewsException
import com.example.entities.response.Result
import retrofit2.Response

suspend fun <R> makeApiCall(
        call: suspend () -> Result<R>,
        errorMessage: Int = 4567
) = try {
    call()
} catch (e: Exception) {
    Result.Error(NewsException(errorMessage))
}

fun <R> analyzeResponse(response: Response<R>): Result<R> {
    return when (response.code()) {
        200 -> {
            val responseBody = response.body()
            Result.Success(responseBody)
        }
        else -> {
            Result.Error(NewsException(response.code()))
        }
    }
}