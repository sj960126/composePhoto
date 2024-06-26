package com.book.data.search.data_source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.book.data.search.mapper.BookMapper
import com.book.data.search.model.BookResponse
import com.book.domain.search.entities.BookEntities
import com.book.domain.search.entities.SearchBookRequest

class SearchBookPagingSource(
    private val remoteDataSource: SearchRemoteDataSource,
    val query : String
) : PagingSource<Int, BookEntities.Document>() {

    override fun getRefreshKey(state: PagingState<Int, BookEntities.Document>): Int? {
        // 이전에 로드된 데이터의 가장 마지막 페이지 번호를 반환
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookEntities.Document> {
        return try {
            // 페이지 넘버와 페이지 사이즈 설정
            val pageNumber = params.key ?: 0 // 초기 페이지는 0으로 설정
            val pageSize = params.loadSize

            val response = remoteDataSource.getSearchBook(SearchBookRequest(query = query, page = pageNumber, size = pageSize))

            LoadResult.Page(
                data = BookMapper.mapToDomain(response).documents,
                prevKey = if (pageNumber == 0) null else pageNumber - 1,
                nextKey = if (response.documents.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            // 에러 발생 시 처리
            LoadResult.Error(e)
        }
    }
}
