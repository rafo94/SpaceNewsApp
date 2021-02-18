package com.example.data.datastore

import com.example.entities.response.News
import com.example.entities.response.Result

interface NewsRepository {

   suspend fun getNews(): Result<List<News>>
}