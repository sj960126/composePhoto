package com.book.domain.bookmark.usecase

import com.book.domain.bookmark.repository.IBookmarkRepository
import com.book.domain.common.entities.BookEntities
import javax.inject.Inject

class AddBookmarkUseCase @Inject constructor(private val repository: IBookmarkRepository) {
    suspend fun invoke(document: BookEntities.Document) = repository.addBookmark(document.apply { isBookMark = true })

}