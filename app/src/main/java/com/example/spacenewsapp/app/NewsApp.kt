package com.example.spacenewsapp.app

import android.app.Application
import com.example.data.di.apiModule
import com.example.data.di.repositoryModule
import com.example.domain.di.interactorsModule
import com.example.spacenewsapp.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApp : Application() {

    val modules = listOf(
            apiModule,
            viewModule,
            repositoryModule,
            interactorsModule
    )

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@NewsApp)
            modules(modules)
        }
    }
}