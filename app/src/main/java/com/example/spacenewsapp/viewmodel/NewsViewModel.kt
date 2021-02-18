package com.example.spacenewsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactors.NewsInteractor
import com.example.entities.response.News
import com.example.entities.response.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(private val newsInteractor: NewsInteractor) : ViewModel() {

    private val _getAllNewsData by lazy { MutableLiveData<List<News>>() }
    var newsResponseLiveData: LiveData<List<News>> = _getAllNewsData

    private val _getAllNewsDataError by lazy { MutableLiveData<String>() }
    var newsResponseErrorLiveData: LiveData<String> = _getAllNewsDataError

    fun getAllNews() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(3000)
            when (val data = newsInteractor.getAllNews()) {
                is Result.Success -> withContext(Dispatchers.Main) {
                    data.data?.let {
                        _getAllNewsData.value = it
                    }
                }
                is Result.Error -> withContext(Dispatchers.Main) {
                    _getAllNewsDataError.value = data.errors.errorMessage
                }
            }
        }
    }
}