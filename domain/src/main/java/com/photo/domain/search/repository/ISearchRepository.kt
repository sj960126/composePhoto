package com.photo.domain.search.repository

import androidx.paging.PagingData
import com.photo.domain.common.entities.BookEntities
import kotlinx.coroutines.flow.Flow

interface ISearchRepository {
    fun getBookList() : Flow<PagingData<BookEntities.Document>>
}