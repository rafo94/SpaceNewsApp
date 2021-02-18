package com.example.data.repository

import com.example.data.dataservice.NewsService
import com.example.data.datastore.NewsRepository
import com.example.data.util.analyzeResponse
import com.example.data.util.makeApiCall
import com.example.entities.response.News
import com.example.entities.response.Result

class NewsRepositoryImpl(private val newsService: NewsService) : NewsRepository {

    override suspend fun getNews(): Result<List<News>> =
            makeApiCall({ analyzeResponse(newsService.getAllNews()) })
}