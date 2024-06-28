package com.book.domain.bookmark.usecase

import com.book.domain.bookmark.repository.IBookmarkRepository
import com.book.domain.common.entities.BookEntities
import javax.inject.Inject


class GetBookmarksUseCase @Inject constructor(private val repository: IBookmarkRepository) {
    suspend fun invoke(): List<BookEntities.Document> = repository.getBookmarks()
}