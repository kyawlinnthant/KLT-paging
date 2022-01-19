package com.example.paging3sample.data.ws

import com.example.paging3sample.helper.Endpoints
import com.example.paging3sample.model.DetailResponse
import com.example.paging3sample.model.ListResponse
import com.example.paging3sample.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(Endpoints.GET_LIST_URL)
    suspend fun fetchMovies(
        @Path("type")type :String,
        @Query("api_key") key: String,
        @Query("page") pageNumber: Int,
    ): Response<ListResponse>

    @GET(Endpoints.GET_DETAIL_URL)
    suspend fun fetchMovieDetail(
        @Path("id") movieId: Long,
        @Query("api_key") key: String,
        @Query("language") language: String
    ): Response<DetailResponse>
}