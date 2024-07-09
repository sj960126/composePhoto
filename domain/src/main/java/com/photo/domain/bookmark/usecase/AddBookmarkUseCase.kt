package com.photo.domain.bookmark.usecase

import com.photo.domain.bookmark.repository.IBookmarkRepository
import com.photo.domain.common.entities.BookEntities
import javax.inject.Inject

class AddBookmarkUseCase @Inject constructor(private val repository: IBookmarkRepository) {
    suspend fun invoke(document: BookEntities.Document) = repository.addBookmark(document.apply { isBookMark = true })

}