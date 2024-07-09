package com.photo.data.search.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.photo.data.bookmark.data_source.local.BookmarkLocalDataSource
import com.photo.data.search.data_source.remote.SearchBookPagingSource
import com.photo.data.search.data_source.remote.SearchRemoteDataSource
import com.photo.domain.common.entities.BookEntities
import com.photo.domain.search.repository.ISearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SearchRepositoryImp @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource,
    private val bookmarkLocalDataSource: BookmarkLocalDataSource
) : ISearchRepository {
    companion object{
        private const val PAGING_SIZE = 20
        private const val QUERY = "안드로이드"
    }
    override fun getBookList(): Flow<PagingData<BookEntities.Document>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                initialLoadSize = PAGING_SIZE ,
                prefetchDistance = PAGING_SIZE
            ),
            pagingSourceFactory = {
                SearchBookPagingSource(
                    remoteDataSource = remoteDataSource,
                    query = QUERY,
                    bookmarkList = runBlocking {bookmarkLocalDataSource.getAllTitles()}
                )
            }
        ).flow


}
