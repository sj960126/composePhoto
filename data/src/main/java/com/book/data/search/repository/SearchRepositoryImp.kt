package com.book.data.search.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.book.data.search.data_source.remote.SearchBookPagingSource
import com.book.data.search.data_source.remote.SearchRemoteDataSource
import com.book.domain.search.entities.BookEntities
import com.book.domain.search.repository.ISearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImp @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource
) : ISearchRepository {
    companion object{
        private const val PAGING_SIZE = 20
        private const val QUERY = "컴퓨터"
    }
    override fun getBookList(): Flow<PagingData<BookEntities.Document>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                initialLoadSize = PAGING_SIZE * 2,  // 첫 로딩 크기 설정
                prefetchDistance = PAGING_SIZE      // 미리 로딩할 아이템의 거리 설정
            ),
            pagingSourceFactory = { SearchBookPagingSource(remoteDataSource, QUERY) }
        ).flow


}
