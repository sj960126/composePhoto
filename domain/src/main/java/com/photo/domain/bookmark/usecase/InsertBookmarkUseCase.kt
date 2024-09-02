package com.photo.domain.bookmark.usecase

import com.photo.domain.bookmark.repository.IBookmarkRepository
import com.photo.domain.common.entities.PhotoEntities
import javax.inject.Inject

class InsertBookmarkUseCase @Inject constructor(private val repository: IBookmarkRepository) {
    suspend operator fun invoke(document: PhotoEntities.Document) = repository.insertBookmark(document.apply { isBookMark = true })

}