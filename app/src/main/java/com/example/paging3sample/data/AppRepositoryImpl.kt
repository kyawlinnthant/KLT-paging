package com.example.paging3sample.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.paging3sample.data.ws.ApiService
import com.example.paging3sample.data.ws.MoviePagingDataSource
import com.example.paging3sample.helper.Endpoints
import com.example.paging3sample.helper.Resource
import com.example.paging3sample.helper.safeApiCall
import com.example.paging3sample.model.DetailResponse
import com.example.paging3sample.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : AppRepository {

    companion object{
        const val POPULAR = "popular"
        const val UPCOMING = "upcoming"
    }


    override suspend fun getPagingMovies(type: String): Flow<PagingData<Movie>> {

        return Pager(
            config = PagingConfig(
                pageSize = MoviePagingDataSource.PAGE_SIZE, // mandatory (others are optional)
//                maxSize = MoviePagingDataSource.MAX_SIZE, //cache size
//                initialLoadSize = MoviePagingDataSource.INITIAL_LOAD_SIZE, //initial load
//                prefetchDistance = 2,
//                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingDataSource(apiService, type) }
        ).flow
    }

    override suspend fun getMovieDetail(
        id: Long,
        language: String
    ): Resource<DetailResponse> {
        val response = apiService.fetchMovieDetail(
            id,
            Endpoints.API_KEY,
            language
        )

        return safeApiCall { response }
    }
}