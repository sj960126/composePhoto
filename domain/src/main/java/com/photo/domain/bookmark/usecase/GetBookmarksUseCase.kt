package com.photo.domain.bookmark.usecase

import com.photo.domain.bookmark.repository.IBookmarkRepository
import com.photo.domain.common.entities.BookEntities
import javax.inject.Inject


class GetBookmarksUseCase @Inject constructor(private val repository: IBookmarkRepository) {
    suspend fun invoke(): List<BookEntities.Document> = repository.getBookmarks()
}