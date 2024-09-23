package com.photo.data.search.di

import com.photo.data.bookmark.data_source.local.BookmarkLocalDataSource
import com.photo.data.search.data_source.remote.ISearchApi
import com.photo.data.search.data_source.remote.SearchRemoteDataSource
import com.photo.data.search.repository.SearchRepositoryImp
import com.photo.domain.search.repository.ISearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SearchModule {
    @Provides
    @Singleton
    fun provideSearchRepository(remoteDataSource: SearchRemoteDataSource,bookmarkLocalDataSource: BookmarkLocalDataSource) : ISearchRepository = SearchRepositoryImp(remoteDataSource,bookmarkLocalDataSource)

    @Provides
    @Singleton
    fun provideSearchRemoteDataSource(searchApi: ISearchApi) : SearchRemoteDataSource = SearchRemoteDataSource(searchApi)

    @Provides
    @Singleton
    fun provideSearchApi(retrofit: Retrofit): ISearchApi = retrofit.create()
}