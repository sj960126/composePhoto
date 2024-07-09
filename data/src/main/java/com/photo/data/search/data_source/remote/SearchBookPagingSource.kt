package com.photo.data.search.data_source.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.photo.data.search.mapper.PhotoMapper
import com.photo.domain.common.entities.PhotoEntities
import com.photo.domain.search.entities.SearchPhotoRequest

class SearchBookPagingSource(
    private val remoteDataSource: SearchRemoteDataSource,
    private val query: String,
    private val bookmarkList : List<String>
) : PagingSource<Int, PhotoEntities.Document>() {

    override fun getRefreshKey(state: PagingState<Int, PhotoEntities.Document>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoEntities.Document> {
        return try {
            val pageNumber = params.key ?: 1
            val response = remoteDataSource.getSearchBook(SearchPhotoRequest(query = query, page = pageNumber, size = params.loadSize))
            LoadResult.Page(
                data = PhotoMapper.toDomain(response, bookMarkList = bookmarkList).documents,
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = if (response.documents.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
