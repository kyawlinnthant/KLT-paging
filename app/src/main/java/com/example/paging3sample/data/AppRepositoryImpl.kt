package com.example.paging3sample.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.paging3sample.data.ws.ApiDataSource
import com.example.paging3sample.data.ws.ApiService
import com.example.paging3sample.data.ws.MoviePagingDataSource
import com.example.paging3sample.di.QualifierAnnotation
import com.example.paging3sample.helper.Endpoints
import com.example.paging3sample.helper.Resource
import com.example.paging3sample.helper.safeApiCall
import com.example.paging3sample.model.DetailResponse
import com.example.paging3sample.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val apiDataSource: ApiDataSource,
    private val apiService: ApiService,
) : AppRepository {


    override suspend fun getPagingMovies(type: String): Flow<PagingData<Movie>> {

        return Pager(
            config = PagingConfig(
                pageSize = MoviePagingDataSource.PAGE_SIZE,
                maxSize = MoviePagingDataSource.MAX_SIZE,
                initialLoadSize = MoviePagingDataSource.INITIAL_LOAD_SIZE,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingDataSource(apiService, type) }
        ).flow
    }

    override suspend fun getMovieDetail(
        id: Long,
        language: String
    ): Resource<DetailResponse> {
        val response = apiDataSource.fetchMovieDetail(
            id,
            Endpoints.API_KEY,
            language
        )

        return safeApiCall { response }
    }
}