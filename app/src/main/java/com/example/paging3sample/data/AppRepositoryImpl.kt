package com.example.paging3sample.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.paging3sample.data.ws.ApiDataSource
import com.example.paging3sample.data.ws.ApiService
import com.example.paging3sample.data.ws.MoviePagerDataSource
import com.example.paging3sample.di.QualifierAnnotation
import com.example.paging3sample.helper.BaseNetworkResponse
import com.example.paging3sample.helper.Resource
import com.example.paging3sample.model.Movie
import com.example.paging3sample.model.ResponseMovies
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val apiDataSource: ApiDataSource,
    private val apiService: ApiService,
    @QualifierAnnotation.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AppRepository, BaseNetworkResponse() {
    override suspend fun getMovies(): Flow<Resource<ResponseMovies>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.getMovieList(1)
                }
            )
        }.flowOn(ioDispatcher)
    }

    override suspend fun getPagingMovies(): Flow<PagingData<Movie>> {

        val loadPageSize = MoviePagerDataSource.LOAD_SIZE
        return Pager(
            config = PagingConfig(
                pageSize = loadPageSize,
                maxSize = loadPageSize + (loadPageSize * 2),
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagerDataSource(apiService) }
        ).flow
    }

}