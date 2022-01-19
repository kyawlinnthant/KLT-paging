package com.example.paging3sample.data.ws

import com.example.paging3sample.model.DetailResponse
import com.example.paging3sample.model.ListResponse
import com.example.paging3sample.model.Movie
import retrofit2.Response

interface ApiDataSource {

    suspend fun getMovieList(
        type: String,
        page: Int,
    ): Response<ListResponse>


    suspend fun fetchMovieDetail(
        movieId: Long,
        key: String,
        language: String
    ): Response<DetailResponse>
}