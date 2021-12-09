package com.example.paging3sample.data.ws

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging3sample.helper.Endpoints
import com.example.paging3sample.model.Movie
import javax.inject.Inject

class MoviePagerDataSource @Inject constructor(
    private val apiService: ApiService
) : PagingSource<Int,Movie> (){

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        var pageNumber = params.key ?: 1
        return try {
            val response = apiService.fetchMovies(Endpoints.API_KEY, pageNumber)
            val pageResponse = response.body()
            val data = pageResponse?.results
            var nextPageNumber : Int? = null
            pageResponse?.results?.let {
                nextPageNumber = pageNumber++
            }
            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = null,
                nextKey = pageNumber
            )
        }catch (
            e : Exception
        ){
            LoadResult.Error(e)
        }
    }
}