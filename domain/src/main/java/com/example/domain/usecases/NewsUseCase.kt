package com.example.domain.usecases

import com.example.data.datastore.NewsRepository
import com.example.domain.interactors.NewsInteractor
import com.example.entities.response.News
import com.example.entities.response.Result

class NewsUseCase(private val newsRepository: NewsRepository) : NewsInteractor {

    override suspend fun getAllNews(): Result<List<News>> {
        return newsRepository.getNews()
    }
}