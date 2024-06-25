package com.book.data.search.data_source.remote

import com.book.data.search.model.BookResponse
import com.book.domain.search.entities.SearchBookRequest
import com.book.data_core.base.AbstractBaseRetrofitRemoteDataSource
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(
    private val iSearchApi: ISearchApi
) : AbstractBaseRetrofitRemoteDataSource(){

    suspend fun getSearchBook(searchBookRequest: SearchBookRequest) : Result<BookResponse?> = runWithHandlingResult {
        iSearchApi.getSearchBook(query = searchBookRequest.query,sort = searchBookRequest.sort,page = searchBookRequest.page,size = searchBookRequest.size, target = searchBookRequest.target)
    }

}