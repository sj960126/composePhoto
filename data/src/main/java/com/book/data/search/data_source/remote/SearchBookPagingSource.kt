package com.book.data.search.data_source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.book.data.search.model.BookResponse

//class SearchBookPagingSource(
//    private val remoteDataSource: SearchRemoteDataSource
//) : PagingSource<Int, BookResponse>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookResponse> {
//        val page = params.key ?: 1
//        return try {
//            val data = remoteDataSource.getSearchBook(page, params.loadSize)
//            LoadResult.Page(
//                data = data,
//                prevKey = if (page == 1) null else page - 1,
//                nextKey = if (data.isEmpty()) null else page + 1
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, BookResponse>): Int? {
//        TODO("Not yet implemented")
//    }
//}
