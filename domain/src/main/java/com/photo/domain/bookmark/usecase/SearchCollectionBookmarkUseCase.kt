package com.photo.domain.bookmark.usecase

import com.photo.domain.bookmark.repository.IBookmarkRepository
import javax.inject.Inject

class SearchCollectionBookmarkUseCase @Inject constructor(private val repository: IBookmarkRepository) {
    suspend fun invoke(keyWord : String) = repository.getByCollection(keyword = keyWord)

}