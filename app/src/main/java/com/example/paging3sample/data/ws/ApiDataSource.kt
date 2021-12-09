package com.example.paging3sample.data.ws

import com.example.paging3sample.model.ResponseMovies
import retrofit2.Response

interface ApiDataSource {

    suspend fun getMovieList(page: Int): Response<ResponseMovies>

}