package com.book.data.search.di

import com.book.data.search.data_source.remote.ISearchApi
import com.book.data.search.data_source.remote.SearchRemoteDataSource
import com.book.data.search.repository.SearchRepositoryImp
import com.book.domain.search.repository.ISearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {
    @Provides
    @Singleton
    fun provideSearchRepository(remoteDataSource: SearchRemoteDataSource) : ISearchRepository = SearchRepositoryImp(remoteDataSource)

    @Provides
    @Singleton
    fun provideSearchRemoteDataSource(searchApi: ISearchApi) : SearchRemoteDataSource = SearchRemoteDataSource(searchApi)

    @Provides
    @Singleton
    fun provideSearchApi(retrofit: Retrofit): ISearchApi {
        return retrofit.create()
    }

}