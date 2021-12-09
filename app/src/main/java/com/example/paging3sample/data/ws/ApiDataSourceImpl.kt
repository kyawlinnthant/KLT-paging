package com.example.paging3sample.data.ws

import com.example.paging3sample.helper.Endpoints
import com.example.paging3sample.model.ResponseMovies
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : ApiDataSource {
    override suspend fun getMovieList(page: Int): Response<ResponseMovies> {
        return apiService.fetchMovies(
            Endpoints.API_KEY,
            page
        )
    }
}