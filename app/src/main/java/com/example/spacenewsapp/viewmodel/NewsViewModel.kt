package com.example.spacenewsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactors.NewsInteractor
import com.example.entities.response.News
import com.example.entities.response.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(private val newsInteractor: NewsInteractor) : ViewModel() {

    private val _getAllNewsData: MutableSharedFlow<List<News>> = MutableSharedFlow(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    var newsResponseData: SharedFlow<List<News>> = _getAllNewsData.asSharedFlow()

    private val _getAllNewsDataError = MutableStateFlow("")
    var newsResponseErrorData: StateFlow<String> = _getAllNewsDataError.asStateFlow()

    fun getAllNews() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(3000)
            when (val data = newsInteractor.getAllNews()) {
                is Result.Success -> {
                    data.data?.let {
                        _getAllNewsData.emit(it)
                    }
                }
                is Result.Error -> withContext(Dispatchers.Main) {
                    _getAllNewsDataError.value = data.errors.errorMessage ?: ""
                }
            }
        }
    }
}