package com.example.domain.interactors

import com.example.entities.response.News
import com.example.entities.response.Result

interface NewsInteractor {
    suspend fun getAllNews(): Result<List<News>>
}