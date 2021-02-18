package com.example.domain.di

import com.example.domain.interactors.NewsInteractor
import com.example.domain.usecases.NewsUseCase
import org.koin.dsl.module

val interactorsModule = module {
    factory<NewsInteractor> { NewsUseCase(get()) }
}