package com.example.paging3sample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paging3sample.data.AppRepository
import com.example.paging3sample.helper.Resource
import com.example.paging3sample.model.ResponseMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoteViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private var _movies = MutableLiveData<Resource<ResponseMovies>>()
    val movies: LiveData<Resource<ResponseMovies>> get() = _movies

    fun getMovies() {

        viewModelScope.launch {

            appRepository.getMovies().collect {
                _movies.postValue(it)
            }

        }
    }
}