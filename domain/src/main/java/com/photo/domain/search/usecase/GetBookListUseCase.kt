package com.photo.domain.search.usecase

import androidx.paging.PagingData
import com.photo.domain.common.entities.BookEntities
import com.photo.domain.search.repository.ISearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookListUseCase @Inject constructor(
    private val repository: ISearchRepository
) {
    operator fun invoke(): Flow<PagingData<BookEntities.Document>> = repository.getBookList()
}
