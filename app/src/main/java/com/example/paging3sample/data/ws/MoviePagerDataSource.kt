package com.example.paging3sample.data.ws

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging3sample.helper.Endpoints
import com.example.paging3sample.model.Movie
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviePagerDataSource @Inject constructor(
    private val apiService: ApiService
) : PagingSource<Int, Movie>() {

    companion object {
        const val INIT_PAGE = 1
        const val LOAD_SIZE = 25
    }

    //The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
//        return INIT_PAGE

        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.

        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val position = params.key ?: INIT_PAGE

        return try {
            val response = apiService.fetchMovies(
                Endpoints.API_KEY,
                position
            )
            val pageResponse = response.body()
            val data = pageResponse?.results

            // By default, initial load size = 3 * NETWORK PAGE SIZE
            // ensure we're not requesting duplicating items at the 2nd request
            val nextKey = if (data.isNullOrEmpty()) {
                null
            } else {
//                pageIndex++
                position + (params.loadSize / LOAD_SIZE)
            }

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = if (position == INIT_PAGE) null else position-1,
                nextKey =  nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}