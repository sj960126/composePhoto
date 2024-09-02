package com.photo.domain.bookmark.usecase

import com.photo.domain.bookmark.repository.IBookmarkRepository
import javax.inject.Inject

class RemoveBookmarkUseCase @Inject constructor(private val repository: IBookmarkRepository) {
    suspend operator fun invoke(title: String) = repository.removeBookmark(title = title)
}