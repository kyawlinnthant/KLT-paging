package com.example.paging3sample.data

import androidx.paging.PagingData
import com.example.paging3sample.helper.Resource
import com.example.paging3sample.model.Movie
import com.example.paging3sample.model.ResponseMovies
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getMovies(): Flow<Resource<ResponseMovies>>
    suspend fun getPagingMovies() : Flow<PagingData<Movie>>
}