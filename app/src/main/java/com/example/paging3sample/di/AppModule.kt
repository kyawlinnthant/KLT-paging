package com.example.paging3sample.di

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3sample.BuildConfig
import com.example.paging3sample.data.AppRepository
import com.example.paging3sample.data.AppRepositoryImpl
import com.example.paging3sample.data.ws.ApiDataSource
import com.example.paging3sample.data.ws.ApiDataSourceImpl
import com.example.paging3sample.data.ws.ApiService
import com.example.paging3sample.helper.Endpoints
import com.example.paging3sample.model.Movie
import com.example.paging3sample.ui.MoviePagingDataAdapter
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppRepository(
        apiDataSource: ApiDataSource,
        apiService: ApiService,
        ): AppRepository =
        AppRepositoryImpl(apiDataSource,apiService)

    @Provides
    @Singleton
    fun provideApiDataSource(apiService: ApiService): ApiDataSource = ApiDataSourceImpl(apiService)

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): ApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Endpoints.BASE_URL)
        .client(okHttpClient)
        .build().create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        //this is for logging profiler
        OkHttpClient.Builder()
            .addInterceptor(OkHttpProfilerInterceptor())
            .addNetworkInterceptor(OkHttpProfilerInterceptor())
            .build()

        //.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

    } else OkHttpClient
        .Builder()
        .build()
}