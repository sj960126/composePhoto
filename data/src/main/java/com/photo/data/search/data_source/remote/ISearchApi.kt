package com.photo.data.search.data_source.remote

import com.photo.data.search.model.PhotoResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ISearchApi {
    companion object{
        private const val SEARCH ="/search"
        private const val PHOTO ="/v2${SEARCH}/image"
    }

    @GET(PHOTO)
    suspend fun fetchPhotos(
        @Query("query") query : String,
        @Query("sort") sort : String?,
        @Query("page") page : Int?,
        @Query("size") size : Int?,
    ) : PhotoResponse

}