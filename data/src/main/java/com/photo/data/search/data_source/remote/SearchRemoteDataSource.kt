package com.photo.data.search.data_source.remote

import com.photo.data.search.model.BookResponse
import com.photo.domain.search.entities.SearchBookRequest
import com.photo.data_core.base.AbstractBaseRetrofitRemoteDataSource
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(
    private val iSearchApi: ISearchApi
) : AbstractBaseRetrofitRemoteDataSource(){
    suspend fun getSearchBook(searchBookRequest: SearchBookRequest) : BookResponse = iSearchApi.getSearchBook(query = searchBookRequest.query,sort = searchBookRequest.sort,page = searchBookRequest.page,size = searchBookRequest.size, target = searchBookRequest.target)

}