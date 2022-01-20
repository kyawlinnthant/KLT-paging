package com.example.paging3sample.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.paging3sample.data.AppRepository
import com.example.paging3sample.di.QualifierAnnotation
import com.example.paging3sample.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val appRepository: AppRepository,
    @QualifierAnnotation.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _movies = MutableLiveData<PagingData<Movie>>()
    val movies: LiveData<PagingData<Movie>> get() = _movies

    fun getPagerMovies(type: String) {

        viewModelScope.launch(ioDispatcher) {
            val data = appRepository.getPagingMovies(type)
            data.collect {
                _movies.postValue(it)
            }
        }
    }
}