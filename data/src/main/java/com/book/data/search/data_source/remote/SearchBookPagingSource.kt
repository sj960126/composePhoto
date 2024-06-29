package com.book.data.search.data_source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.book.data.search.mapper.BookMapper
import com.book.domain.common.entities.BookEntities
import com.book.domain.search.entities.SearchBookRequest

class SearchBookPagingSource(
    private val remoteDataSource: SearchRemoteDataSource,
    private val query: String,
    private val bookmarkList : List<String>
) : PagingSource<Int, BookEntities.Document>() {

    override fun getRefreshKey(state: PagingState<Int, BookEntities.Document>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookEntities.Document> {
        return try {
            val pageNumber = params.key ?: 1
            val response = remoteDataSource.getSearchBook(SearchBookRequest(query = query, page = pageNumber, size = params.loadSize))
            LoadResult.Page(
                data = BookMapper.toDomain(response, bookMarkList = bookmarkList).documents,
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = if (response.documents.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
