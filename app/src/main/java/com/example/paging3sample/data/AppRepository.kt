package com.example.paging3sample.data

import androidx.paging.PagingData
import com.example.paging3sample.helper.Resource
import com.example.paging3sample.model.DetailResponse
import com.example.paging3sample.model.Movie
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getPagingMovies(type: String) : Flow<PagingData<Movie>>
    suspend fun getMovieDetail(id: Long,language : String) : Resource<DetailResponse>
}