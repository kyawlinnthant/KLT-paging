package com.example.paging3sample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.paging3sample.data.AppRepository
import com.example.paging3sample.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoteViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    init {
        getPagerMovies()
    }

    private lateinit var _movies: Flow<PagingData<Movie>>
    val movies: Flow<PagingData<Movie>> get() = _movies

    private fun getPagerMovies() {

        viewModelScope.launch {

            appRepository.getPagingMovies().collect {
                _movies = appRepository.getPagingMovies()
            }

        }
    }
}