package com.photo.data.search.data_source.remote

import com.photo.data.search.model.PhotoResponse
import com.photo.domain.search.entities.SearchPhotoRequest
import com.photo.data.data_core.base.AbstractBaseRetrofitRemoteDataSource
import javax.inject.Inject

internal class SearchRemoteDataSource @Inject constructor(
    private val iSearchApi: ISearchApi
) : AbstractBaseRetrofitRemoteDataSource(){
    suspend fun fetchPhotos(searchPhotoRequest: SearchPhotoRequest) : PhotoResponse = iSearchApi.fetchPhotos(query = searchPhotoRequest.query,sort = searchPhotoRequest.sort,page = searchPhotoRequest.page,size = searchPhotoRequest.size,)

}