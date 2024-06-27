package com.book.domain.search.usecase

import androidx.paging.PagingData
import com.book.domain.search.entities.BookEntities
import com.book.domain.search.repository.ISearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookListUseCase @Inject constructor(
    private val repository: ISearchRepository
) {
    operator fun invoke(): Flow<PagingData<BookEntities.Document>> = repository.getBookList()

}
