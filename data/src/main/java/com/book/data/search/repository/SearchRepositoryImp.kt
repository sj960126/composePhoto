package com.book.data.search.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.book.data.search.data_source.remote.SearchRemoteDataSource
import com.book.domain.search.entities.BookEntities
import com.book.domain.search.repository.ISearchRepository
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImp(
    private val remoteDataSource: SearchRemoteDataSource
) : ISearchRepository {

    override fun getSearchBook(): Flow<PagingData<BookEntities>> = Pager(PagingConfig(pageSize = 20)) {
        YourPagingSource(remoteDataSource)
    }.flow
}
