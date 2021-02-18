package com.example.data.di

import com.example.data.dataservice.NewsService
import com.example.data.datastore.NewsRepository
import com.example.data.repository.NewsRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    single<Retrofit> {
        Retrofit.Builder()
                .baseUrl("https://www.spaceflightnewsapi.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .apply {
                    client(
                            OkHttpClient.Builder()
                                    .addInterceptor(HttpLoggingInterceptor().apply {
                                        level = HttpLoggingInterceptor.Level.BODY
                                    })
                                    .build()
                    )
                }
                .build()
    }

    single<NewsService> { get<Retrofit>().create(NewsService::class.java) }
}

val repositoryModule = module {
    single<NewsRepository> { NewsRepositoryImpl(get()) }
}