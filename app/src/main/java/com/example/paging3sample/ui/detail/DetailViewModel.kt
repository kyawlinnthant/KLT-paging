package com.example.paging3sample.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paging3sample.data.AppRepository
import com.example.paging3sample.di.QualifierAnnotation
import com.example.paging3sample.helper.Resource
import com.example.paging3sample.model.DetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val appRepository: AppRepository,
    @QualifierAnnotation.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    //for detail
    private val _movie = MutableLiveData<Resource<DetailResponse>>()
    val movie: LiveData<Resource<DetailResponse>> get() = _movie

    fun getMovieDetail(
        id: Long
    ) {
        viewModelScope.launch(ioDispatcher) {
            _movie.postValue(
                appRepository.getMovieDetail(
                    id, "en"
                )
            )
        }
    }
}