package com.book.domain.bookmark.usecase

import com.book.domain.bookmark.repository.IBookmarkRepository
import com.book.domain.common.entities.BookEntities

/**
 * @author songhyeonsu
 * Created 6/28/24 at 11:01 AM
 */
class RemoveBookmarkUseCase(private val repository: IBookmarkRepository) {
    suspend fun invoke(document: BookEntities.Document) = repository.removeBookmark(document)
}