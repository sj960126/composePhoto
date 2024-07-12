package com.photo.domain.bookmark.usecase

import com.photo.domain.bookmark.repository.IBookmarkRepository
import com.photo.domain.common.entities.PhotoEntities
import javax.inject.Inject


class FetchAllBookmarksUseCase @Inject constructor(private val repository: IBookmarkRepository) {
    suspend fun invoke(): List<PhotoEntities.Document> = repository.fetchAllBookmarks()
}