package com.example.spacenewsapp.di

import com.example.spacenewsapp.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel { NewsViewModel(get()) }
}