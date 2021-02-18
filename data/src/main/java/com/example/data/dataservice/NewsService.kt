package com.example.data.dataservice

import com.example.entities.response.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("api/v2/articles")
    suspend fun getAllNews(@Query("_limit") limit:Long = 80): Response<List<News>>
}