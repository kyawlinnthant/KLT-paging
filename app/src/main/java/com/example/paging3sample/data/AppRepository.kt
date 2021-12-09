package com.example.paging3sample.data

import com.example.paging3sample.helper.Resource
import com.example.paging3sample.model.ResponseMovies
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getMovies(): Flow<Resource<ResponseMovies>>
}