package com.photo.domain.search.repository

import androidx.paging.PagingData
import com.photo.domain.common.entities.PhotoEntities
import kotlinx.coroutines.flow.Flow

interface ISearchRepository {
    fun getPhotoList(keyword : String) : Flow<PagingData<PhotoEntities.Document>>
}