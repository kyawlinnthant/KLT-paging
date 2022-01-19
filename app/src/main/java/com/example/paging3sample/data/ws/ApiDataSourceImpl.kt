package com.example.paging3sample.data.ws

import com.example.paging3sample.helper.Endpoints
import com.example.paging3sample.model.DetailResponse
import com.example.paging3sample.model.ListResponse
import com.example.paging3sample.model.Movie
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : ApiDataSource {

    companion object {
        const val POPULAR = "popular"
        const val UPCOMING = "upcoming"
    }

    override suspend fun getMovieList(
        type: String,
        page: Int,
    ): Response<ListResponse> {
        return apiService.fetchMovies(
            type,
            Endpoints.API_KEY,
            page,
        )
    }

    override suspend fun fetchMovieDetail(
        movieId: Long,
        key: String,
        language: String
    ): Response<DetailResponse> {
        return apiService.fetchMovieDetail(
            movieId,
            Endpoints.API_KEY,
            language
        )
    }
}

